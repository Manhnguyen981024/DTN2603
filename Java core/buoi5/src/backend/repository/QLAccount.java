package backend.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import backend.database.DatabaseConfig;
import backend.mapper.AccountMapper;
import entity.Account;

public class QLAccount implements IQLAccount {
    private final String sql = """
                        SELECT *
                        FROM account             AS acc
                            LEFT JOIN department AS dep
                                ON  acc.department_id = dep.department_id
                            LEFT JOIN `position` AS p
                                ON  acc.position_id = p.position_id
                        """;

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection()){
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                accounts.add(AccountMapper.mapperToAccount(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return accounts;
    }
}
