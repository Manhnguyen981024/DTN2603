package com.dtn2603.utils;

import com.dtn2603.exception.DataAccessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class JdbcUtils {
    private static final DatabaseConfig DATABASE_CONFIG = DatabaseConfig.load();

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE_CONFIG.getUrl(),
                DATABASE_CONFIG.getUsername(),
                DATABASE_CONFIG.getPassword()
        );
    }

    public static String toInValues(Collection<?> values) {
        return values.stream()
                .map(value -> "'" + value.toString().replace("'", "''") + "'")
                .collect(Collectors.joining(","));
    }

    public static <T> Set<T> queryColumn(String sql) {
        Set<T> values = new LinkedHashSet<>();
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    values.add(getValue(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot access to database, please try again!", e);
        }
        return values;
    }

    private static <T> T getValue(ResultSet resultSet) throws SQLException {
        @SuppressWarnings("unchecked")
        T value = (T) resultSet.getObject(1);
        return value;
    }
}
