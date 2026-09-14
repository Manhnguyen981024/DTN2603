package com.dtn2603.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@AllArgsConstructor
@Getter
public final class DatabaseConfig {
    private static final String CONFIG_FILE = "application.properties";

    private final String url;
    private final String username;
    private final String password;

    public static DatabaseConfig load() {
        Properties properties = new Properties();

        try (InputStream inputStream = DatabaseConfig.class
                .getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException(
                        "Cannot find " + CONFIG_FILE + " on the classpath"
                );
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Cannot read " + CONFIG_FILE, e
            );
        }

        return new DatabaseConfig(
                getRequired(properties, "db.url"),
                getRequired(properties, "db.username"),
                getRequired(properties, "db.password")
        );
    }

    private static String getRequired(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required property: " + key
            );
        }
        return value.trim();
    }
}
