package com.dtn2603.mapper;

import com.dtn2603.entity.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentMapper {

    public static Department mapToDepartment(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }
        return new Department(
                resultSet.getInt("department_id"),
                resultSet.getString("department_name")
        );
    }
    
}
