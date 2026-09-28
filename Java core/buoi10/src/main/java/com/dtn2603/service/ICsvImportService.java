package com.dtn2603.service;

import com.dtn2603.constant.AppConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public interface ICsvImportService<T> {

    int getRequiredFieldCount();

    String getErrorLogFileName();

    T parseFields(List<String> fields);

    String validateFields(T item);

    Map<String, Set<String>> loadContext(List<T> items);

    Map<String, String> extractKeys(T item);

    String validateUniqueKeys(T item, Map<String, Set<String>> context,
                              Map<String, Set<String>> seenKeys);

    int saveAll(List<T> items);

    default String getLogHeader() {
        return "error_message";
    }

    default Map<String, Integer> importCSV(String filename) {
        if (Objects.isNull(filename) || filename.isBlank()) {
            throw new IllegalArgumentException("Filename can't be null");
        }
        if (!filename.toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("File must end with .csv");
        }
        File file = new File(filename);
        if (!file.isFile()) {
            throw new IllegalArgumentException("File does not exist");
        }

        List<String> errorLines = new ArrayList<>();
        String headerLine;
        ParsedBatch<T> parsed;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            headerLine = br.readLine();
            if (Objects.isNull(headerLine)) {
                throw new IllegalArgumentException("File is empty");
            }
            parsed = parseDataRows(br, errorLines);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read file " + filename, e);
        }

        int successCount = saveValidItems(parsed.parsedItems(), parsed.originalLines(), errorLines);

        if (!errorLines.isEmpty()) {
            errorLines.add(0, headerLine + "," + getLogHeader());
            try {
                writeErrorLog(errorLines);
            } catch (IOException e) {
                System.err.println("Cannot write error log file: " + e.getMessage());
            }
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("totalCount", parsed.dataRowCount());
        result.put("successCount", successCount);
        result.put("errorCount", parsed.dataRowCount() - successCount);
        return result;
    }

    private ParsedBatch<T> parseDataRows(BufferedReader br, List<String> errorLines) throws IOException {
        List<T> parsedItems = new ArrayList<>();
        List<String> originalLines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isBlank()) {
                continue;
            }
            List<String> fields = splitFields(line);
            if (fields.size() < getRequiredFieldCount()) {
                errorLines.add(toErrorLine(line, String.format(
                        "Invalid fields in CSV: expected at least %d fields but got %d",
                        getRequiredFieldCount(), fields.size())));
                continue;
            }
            
            try {
                parsedItems.add(parseFields(fields));
                originalLines.add(line);
            } catch (IllegalArgumentException e) {
                errorLines.add(toErrorLine(line, e.getMessage()));
            }
        }
        return new ParsedBatch<>(parsedItems, originalLines, parsedItems.size() + errorLines.size());
    }

    private int saveValidItems(List<T> items, List<String> itemLines, List<String> errorLines) {
        if (items.isEmpty()) {
            return 0;
        }

        Map<String, Set<String>> context = loadContext(items);
        Map<String, Set<String>> seenKeys = new HashMap<>();

        for (String fieldName : extractKeys(items.get(0)).keySet()) {
            seenKeys.computeIfAbsent(fieldName, k -> new HashSet<>());
        }

        List<T> validItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            String errorMessage = validateFields(item);
            if (Objects.isNull(errorMessage)) {
                errorMessage = validateUniqueKeys(item, context, seenKeys);
            }
            if (Objects.nonNull(errorMessage)) {
                errorLines.add(toErrorLine(itemLines.get(i), errorMessage));
                continue;
            }

            for (Map.Entry<String, String> entry : extractKeys(item).entrySet()) {
                seenKeys.get(entry.getKey()).add(entry.getValue());
            }
            validItems.add(item);
        }

        return validItems.isEmpty() ? 0 : saveAll(validItems);
    }

    default void writeErrorLog(List<String> errorLines) throws IOException {
        File errorFile = getErrorLogFile();
        File parentDir = errorFile.getParentFile();
        if (Objects.nonNull(parentDir) && !parentDir.exists() && !parentDir.mkdirs()) {
            throw new IOException("Cannot create log directory " + parentDir);
        }
        try (FileWriter writer = new FileWriter(errorFile)) {
            for (String errorLine : errorLines) {
                writer.write(errorLine);
                writer.write(System.lineSeparator());
            }
        }
    }

    default File getErrorLogFile() {
        return new File(AppConstants.LOG_DIRECTORY + File.separator + getErrorLogFileName());
    }

    default List<String> splitFields(String line) {
        return Arrays.stream(line.split(",", -1)).collect(Collectors.toList());
    }

    default String toErrorLine(String line, String errorMessage) {
        List<String> fields = splitFields(line);
        while (fields.size() < getRequiredFieldCount()) {
            fields.add("");
        }
        fields.add(errorMessage);
        return String.join(",", fields);
    }

}

record ParsedBatch<T>(List<T> parsedItems, List<String> originalLines, int dataRowCount) {
}