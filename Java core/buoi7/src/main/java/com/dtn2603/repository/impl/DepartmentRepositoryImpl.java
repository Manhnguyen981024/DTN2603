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
}
