package com.dtn2603.repository.impl;

import com.dtn2603.entity.Position;
import com.dtn2603.exception.DataAccessException;
import com.dtn2603.mapper.PositionMapper;
import com.dtn2603.repository.IPositionRepository;
import com.dtn2603.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionRepositoryImpl implements IPositionRepository {
    private final String SQL_STRING = """
                                SELECT *
                                FROM `position`
                        """;
    @Override
    public List<Position> findAll() {
        List<Position> positions = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_STRING);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                positions.add(PositionMapper.mapToPosition(resultSet));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Cannot access to database, please try again!", e);
        }

        return positions;
    }
}
