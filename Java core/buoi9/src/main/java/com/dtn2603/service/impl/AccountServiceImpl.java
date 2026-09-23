package com.dtn2603.service.impl;

import com.dtn2603.constant.AppConstants;
import com.dtn2603.entity.Account;
import com.dtn2603.entity.Department;
import com.dtn2603.entity.Position;
import com.dtn2603.entity.enums.Gender;
import com.dtn2603.exception.ResourceNotFoundException;
import com.dtn2603.repository.IAccountRepository;
import com.dtn2603.service.IAccountService;
import com.dtn2603.service.IDepartmentService;
import com.dtn2603.service.IPositionService;
import com.dtn2603.utils.ValidationUtils;
import lombok.AllArgsConstructor;

import java.io.*;
import java.util.*;

@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final int MIN_LENGTH = 6;
    private final int MAX_LENGTH = 100;
    private final IAccountRepository accountRepository;
    private final IDepartmentService departmentService;
    private final IPositionService positionService;

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public boolean addAccount(Account account) {
        String message = validateAccount(account);
        if(Objects.nonNull(message)) {
            throw new IllegalArgumentException(message);
        }
        return accountRepository.save(account);
    }

    @Override
    public boolean updateAccount(Account account) {
         if (Objects.isNull(account)) {
             throw new IllegalArgumentException("Account can't be null");
         }

        if (!ValidationUtils.isValidLength(account.getUsername(), MIN_LENGTH, MAX_LENGTH)) {
            throw new IllegalArgumentException(String.format("Account username length must from %d to %d", MIN_LENGTH, MAX_LENGTH));
        }

        if (account.getAccountId() <= 0) {
            throw new IllegalArgumentException("Account id must be positive");
        }

        if (Objects.isNull(accountRepository.findById(account.getAccountId()))) {
            throw new ResourceNotFoundException("Account id not found");
        }

        Account existedAccount = accountRepository.findByUsername(account.getUsername());
        if (Objects.nonNull(existedAccount)
                && !Objects.equals(existedAccount.getAccountId(), account.getAccountId())) {
            throw new IllegalArgumentException("Account username already exist");
        }
        return accountRepository.update(account);
    }

    @Override
    public boolean deleteAccountById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Account id must be positive");
        }

        if (Objects.isNull(accountRepository.findById(id))) {
            throw new ResourceNotFoundException("Account id not found");
        }
        return accountRepository.deleteById(id);
    }

    @Override
    public Map<String, Integer> importAccountFromCSV(String filename) {
        int totalCount = 0;
        Map<String, Integer> resultMap = new HashMap<>();
        if (Objects.isNull(filename)) {
            throw new IllegalArgumentException("Filename can't be null");
        }

        if (!filename.endsWith(".csv")) {
            throw new IllegalArgumentException("File must end with .csv");
        }

        File file = new File(filename);
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist");
        }

        List<Account> accounts = new ArrayList<>();
        List<String> errorLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            errorLines.add(br.readLine() +",error_message");
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 6) {
                    errorLines.add(line + ", " + String.format("Invalid fields in CSV: %s", line));
                    continue;
                }
                Account account = new Account();
                account.setEmail(fields[0]);
                account.setUsername(fields[1]);
                account.setFullName(fields[2]);
                account.setDepartment(fields[3].isEmpty() ? null : new Department(Integer.parseInt(fields[3])));
                account.setPosition(fields[4].isEmpty() ? null : new Position(Integer.parseInt(fields[4])));
                account.setGender("M".equals(fields[5]) || "F".equals(fields[5]) ? Gender.valueOf(fields[5]) : Gender.U);

                String errorMessage = validateAccount(account);
                if (Objects.nonNull(errorMessage)) {
                    errorLines.add(line + "," + errorMessage);
                    continue;
                }
                accounts.add(account);
            }
            if (!accounts.isEmpty()) {
                totalCount = accountRepository.saveAll(accounts);
            }

            if (errorLines.size() > 1) {
                writeErrorLog(errorLines);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        resultMap.put("totalCount", accounts.size() + errorLines.size());
        resultMap.put("successCount", totalCount);
        resultMap.put("errorCount", errorLines.size());
        return resultMap;
    }

    private void writeErrorLog(List<String> errorLines) throws IOException {
        File errorFile = new File(AppConstants.IMPORT_ACCOUNT_ERROR_LOG);
        if (!errorFile.exists()) {
            errorFile.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(errorFile));
        for (String errorLine : errorLines) {
            writer.write(errorLine);
            writer.newLine();
        }
        writer.close();
    }

    private String validateAccount(Account account) {
        List<String> errors = new ArrayList<>();
        if (Objects.isNull(account)) {
            errors.add("Account can't be null");
        }

        if (!ValidationUtils.isValidLength(account.getUsername(), MIN_LENGTH, MAX_LENGTH)) {
            errors.add(String.format("Account username length must from %d to %d", MIN_LENGTH, MAX_LENGTH));
        }

        if (!ValidationUtils.isValidLength(account.getEmail(), MIN_LENGTH, MAX_LENGTH)) {
            errors.add(String.format(String.format("Account email length must from %d to %d", MIN_LENGTH, MAX_LENGTH)));
        }

        if (!ValidationUtils.isValidEmail(account.getEmail())) {
            errors.add("Account email is not valid!");
        }

        if (!ValidationUtils.isValidLength(account.getFullName(), MIN_LENGTH, MAX_LENGTH)) {
            errors.add(String.format("Account fullname length must from %d to %d", MIN_LENGTH, MAX_LENGTH));
        }

        if (errors.isEmpty()
                && Objects.nonNull(accountRepository.findByUsername(account.getUsername()))) {
            errors.add("Account username already exist");
        }

        if (errors.isEmpty()
                && Objects.nonNull(accountRepository.findByEmail(account.getEmail()))) {
            errors.add("Account email already exist");
        }

        if (errors.isEmpty()
                && Objects.nonNull(account.getDepartment())
                && Objects.isNull(departmentService.getDepartmentById(account.getDepartment().getDepartmentId()))){
            errors.add("Department don't exist");
        }

        if (errors.isEmpty()
                && Objects.nonNull(account.getPosition())
                && Objects.isNull(positionService.getPositionById(account.getPosition().getPositionId()))){
            errors.add("Position don't exist");
        }

        return errors.isEmpty() ? null : String.join(",", errors);
    }
}

