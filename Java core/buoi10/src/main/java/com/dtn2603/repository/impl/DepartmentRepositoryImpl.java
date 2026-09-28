package com.dtn2603.repository.impl;

import com.dtn2603.entity.Department;
import com.dtn2603.exception.DataAccessException;
import com.dtn2603.mapper.DepartmentMapper;
import com.dtn2603.repository.IDepartmentRepository;
import com.dtn2603.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DepartmentRepositoryImpl implements IDepartmentRepository {
    private final String SQL_STRING = """
                                SELECT *
                                FROM department
                        """;
    private final String SQL_QUERY_BY_ID =  """
                                SELECT *
                                FROM department 
                                WHERE department_id = ?
                        """;
    private final String SQL_INSERT = """
                                INSERT INTO department (department_name)
                                VALUES (?)
                        """;
    @Override
    public int saveAll(List<Department> departments) {
        if (departments.isEmpty()) {
            return 0;
        }
        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_INSERT)) {

            for (Department department : departments) {
                statement.setString(1, department.getDepartmentName());
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
        String sql = "SELECT department_id FROM department WHERE department_id IN ("
                + JdbcUtils.toInValues(ids) + ")";
        return JdbcUtils.queryColumn(sql);
    }
    
    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_STRING);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                departments.add(DepartmentMapper.mapToDepartment(resultSet));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot access to database, please try again!", e);
        }

        return departments;
    }

    @Override
    public Department findById(Integer id) {
        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_QUERY_BY_ID)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return DepartmentMapper.mapToDepartment(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Cannot access to database, please try again!", e);
        }
        return null;
    }
}
