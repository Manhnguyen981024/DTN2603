package backend.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private static final String SQL_FIND_BY_ID = """
                                SELECT *
                                FROM department
                                WHERE department_id = ?
                        """;
    private static final String SQL_INSERT = """
                                INSERT INTO department (department_name)
                                VALUES (?)
                        """;
    private static final String SQL_UPDATE = """
                                UPDATE department
                                SET department_name = ?
                                WHERE department_id = ?
                        """;
    private static final String SQL_DELETE = """
                                DELETE FROM department
                                WHERE department_id = ?
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }

    @Override
    public Department findById(Integer id) {
        try (
            Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(SQL_FIND_BY_ID)) {
            
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return DepartmentMapper.mapToDepartment(resultSet);
            }   
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Department save(Department department) {
        try (
            Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(SQL_INSERT)) {
            statement.setString(1, department.getDepartmentName());
            int cnt = statement.executeUpdate();
            if (cnt > 0) {
                System.out.println("Insert new department successfully!");
            } else {
                System.out.println("Failed to save department.");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return department;
    }

    @Override
    public Department update(Department department) {
        try (
            Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, department.getDepartmentName());
            statement.setInt(2, department.getDepartmentId());
            
            int cnt = statement.executeUpdate();
            if (cnt > 0) {
                System.out.println("Update department name successfully!");
            } else {
                System.out.println("Failed to update department name.");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return department;
    }

    @Override
    public void deleteById(Integer id) {
        try (
            Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(SQL_DELETE)) {
            statement.setInt(1, id);
            int cnt = statement.executeUpdate();
            if (cnt > 0) {
                System.out.println("Delete department successfully!");
            } else {
                System.out.println("Failed to delete department.");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
