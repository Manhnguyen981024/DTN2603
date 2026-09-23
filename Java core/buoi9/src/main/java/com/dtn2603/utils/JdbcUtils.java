package com.dtn2603.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    private static final DatabaseConfig DATABASE_CONFIG = DatabaseConfig.load();

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE_CONFIG.getUrl(),
                DATABASE_CONFIG.getUsername(),
                DATABASE_CONFIG.getPassword()
        );
    }
}
