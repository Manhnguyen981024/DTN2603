package backend.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Department;

public class DepartmentMapper {

    public static Department mapperToDepartment(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }
        return new Department(resultSet.getInt("department_id"), resultSet.getString("department_name"));
    }
    
}
