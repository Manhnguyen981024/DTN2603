package backend.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import backend.database.DatabaseConfig;
import backend.mapper.PositionMapper;
import entity.Position;

public class QLPosition implements IQLPosition {
    private static final String SQL_STRING = """
                                SELECT *
                                FROM `position`
                        """;
    private static final String SQL_FIND_BY_ID = """
                                SELECT *
                                FROM `position`
                                WHERE position_id = ?
                        """;
    private static final String SQL_INSERT = """
                                INSERT INTO `position` (position_name)
                                VALUES (?)
                        """;
    private static final String SQL_UPDATE = """
                                UPDATE `position`
                                SET position_name = ?
                                WHERE position_id = ?
                        """;
    private static final String SQL_DELETE = """
                                DELETE FROM `position`
                                WHERE position_id = ?
                        """;
        private static final String SQL_POSITION_NAME_TYPE = """
                                                                SELECT COLUMN_TYPE
                                                                FROM information_schema.COLUMNS
                                                                WHERE TABLE_SCHEMA = DATABASE()
                                                                    AND TABLE_NAME = 'position'
                                                                    AND COLUMN_NAME = 'position_name'
                                                """;

    @Override
    public List<Position> findAll() {
        List<Position> positions = new ArrayList<>();

        try (
            Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(SQL_STRING);
            ResultSet resultSet = statement.executeQuery()) {
           
            while (resultSet.next()) {
                positions.add(PositionMapper.mapToPosition(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return positions;
    }

    @Override
    public Position findById(Integer id) {
        try (
            Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return PositionMapper.mapToPosition(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Position save(Position position) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            updatePositionNameEnum(conn, position.getPositionName());

            try (PreparedStatement statement = conn.prepareStatement(SQL_INSERT)) {
            statement.setString(1, position.getPositionName());
            statement.executeUpdate();
            return position;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create position", e);
        }
    }

    @Override
    public Position update(Position position) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            updatePositionNameEnum(conn, position.getPositionName());

            try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE)) {
                statement.setString(1, position.getPositionName());
                statement.setInt(2, position.getPositionId());

                if (statement.executeUpdate() == 0) {
                    throw new IllegalArgumentException("Position id not found");
                }
                return position;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot update position", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_DELETE)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() == 0) {
                throw new IllegalArgumentException("Position id not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete position", e);
        }
    }

    private void updatePositionNameEnum(Connection conn, String newPositionName) throws SQLException {
        Set<String> positionNames = readPositionNameEnumValues(conn);
        positionNames.add(newPositionName);

        String enumValues = positionNames.stream()
                .map(this::quoteSqlString)
                .reduce((left, right) -> left + ", " + right)
                .orElseThrow(() -> new IllegalArgumentException("Position name cannot be empty"));

        String alterSql = "ALTER TABLE `position` MODIFY COLUMN position_name ENUM ("
                + enumValues + ") NOT NULL";

        try (PreparedStatement statement = conn.prepareStatement(alterSql)) {
            statement.executeUpdate();
        }
    }

    private Set<String> readPositionNameEnumValues(Connection conn) throws SQLException {
        Set<String> positionNames = new LinkedHashSet<>();

        try (PreparedStatement statement = conn.prepareStatement(SQL_POSITION_NAME_TYPE);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                Matcher matcher = Pattern.compile("'((?:''|[^'])*)'")
                        .matcher(resultSet.getString("COLUMN_TYPE"));
                while (matcher.find()) {
                    positionNames.add(matcher.group(1).replace("''", "'"));
                }
            }
        }

        return positionNames;
    }

    private String quoteSqlString(String value) {
        return "'" + value.replace("'", "''") + "'";
    }
}
