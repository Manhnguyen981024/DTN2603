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
import java.util.Set;

public class PositionRepositoryImpl implements IPositionRepository {
    private final String SQL_STRING = """
                                SELECT *
                                FROM `position`
                        """;
    private final String SQL_QUERY_BY_ID = """
                                SELECT *
                                FROM `position`
                                where `position_id` = ?
                        """;
    private final String SQL_INSERT = """
                                INSERT INTO `position` (position_id, position_name)
                                VALUES (?, ?)
                        """;
    @Override
    public int saveAll(List<Position> positions) {
        if (positions.isEmpty()) {
            return 0;
        }
        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_INSERT)) {

            for (Position position : positions) {
                statement.setInt(1, position.getPositionId());
                statement.setString(2, position.getPositionName().name());
                statement.addBatch();
            }
            int[] results = statement.executeBatch();
            return results.length;
        } catch (SQLException e) {
            throw new DataAccessException("Cannot access to database, please try again!", e);
        }
    }

    @Override
    public Set<Integer> findExistingIds(List<Integer> ids) {
        if (ids.isEmpty()) {
            return Set.of();
        }
        String sql = "SELECT position_id FROM `position` WHERE position_id IN ("
                + JdbcUtils.toInValues(ids) + ")";
        return JdbcUtils.queryColumn(sql);
    }
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

    @Override
    public Position findById(Integer id) {
        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_QUERY_BY_ID)){

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return PositionMapper.mapToPosition(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot access to database, please try again!", e);
        }
        return null;
    }
}
