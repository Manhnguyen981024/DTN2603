package backend.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import backend.database.DatabaseConfig;
import backend.mapper.DepartmentMapper;
import entity.Department;

public class QLDepartment implements IQLDepartment {
    private static final String SQL_STRING = """
                                SELECT *
                                FROM department
                        """;

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();

        try (
            Connection conn = DatabaseConfig.getConnection();            
            PreparedStatement statement = conn.prepareStatement(SQL_STRING);
            ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                departments.add(DepartmentMapper.mapToDepartment(resultSet));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return departments;
    }
}
