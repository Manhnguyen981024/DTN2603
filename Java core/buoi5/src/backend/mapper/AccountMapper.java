package backend.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import entity.Account;
import entity.Department;
import entity.Position;
import entity.enums.PositionName;

public class AccountMapper {

    public static Account mapToAccount(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }

        int id = resultSet.getInt("account_id");
        String username = resultSet.getString("email");
        String password = resultSet.getString("username");
        String email = resultSet.getString("fullname");
        String departmentId = resultSet.getString("department_id");
        String departmentName = resultSet.getString("department_name");
        String position_id = resultSet.getString("position_id");
        String positionName = resultSet.getString("position_name");
        LocalDate createDate = resultSet.getDate("create_date").toLocalDate();

        Department department = new Department();
        if (departmentId != null) {
            department.setDepartmentId(Integer.parseInt(departmentId));
            department.setDepartmentName(departmentName);
        }

        Position position = new Position();
        if (position_id != null) {
            position.setPositionId(Integer.parseInt(position_id));
            position.setPositionName(PositionName.valueOf(positionName));
        }
        return new Account(id, username, password, email, department, position, createDate);
    }

}
