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
