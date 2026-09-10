package backend.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import backend.database.DatabaseConfig;
import backend.mapper.PositionMapper;
import entity.Position;

public class QLPosition implements IQLPosition {
    private static final String SQL_STRING = """
                                SELECT *
                                FROM `position`
                        """;

    @Override
    public List<Position> findAll() {
        List<Position> positions = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(SQL_STRING);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                positions.add(PositionMapper.mapperToPosition(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return positions;
    }
}
