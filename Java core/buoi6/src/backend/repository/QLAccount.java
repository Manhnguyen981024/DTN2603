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
    private final String sqlQuery = """
                        SELECT *
                        FROM account             AS acc
                            LEFT JOIN department AS dep
                                ON  acc.department_id = dep.department_id
                            LEFT JOIN `position` AS p
                                ON  acc.position_id = p.position_id
                        """;

    private final String sqlQueryById = """
                        SELECT *
                        FROM account             AS acc
                            LEFT JOIN department AS dep
                                ON  acc.department_id = dep.department_id
                            LEFT JOIN `position` AS p
                                ON  acc.position_id = p.position_id
                        WHERE acc.account_id = ?
                        """;

    private final String sqlInsert = """
                       INSERT INTO account 
                        ( 
                              email 
                            , username 
                            , fullname 
                            , department_id 
                            , position_id 
                            , gender 
                        ) 
                        VALUES 
                        ( 
                              ?
                            , ?
                            , ?
                            , ? 
                            , ? 
                            , ? 
                        ) """;

    private final String sqlUpdate = """
                        UPDATE account
                        SET username = ?
                        WHERE account_id = ? """ ;

    private final String sqlDelete = """
                        DELETE FROM account
                        WHERE account_id = ? """ ;
    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try (
            Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            ResultSet resultSet = statement.executeQuery(); ){
            while (resultSet.next()) {
                accounts.add(AccountMapper.mapToAccount(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        
        return accounts;
    }

    @Override
    public Account save(Account account) {
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlInsert);) {
            statement.setString(1, account.getEmail());
            statement.setString(2, account.getUsername());
            statement.setString(3, account.getFullName());
            
            if (account.getDepartment() == null) {
                statement.setNull(4, java.sql.Types.INTEGER);
            } else {
                statement.setInt(4, account.getDepartment().getDepartmentId());
            }

            if (account.getPosition() == null) {
                statement.setNull(5, java.sql.Types.INTEGER);
            } else {
                statement.setInt(5, account.getPosition().getPositionId());
            }

            statement.setString(6, account.getGender().name());
            
            int success = statement.executeUpdate();
            if (success > 0) {
                System.out.println("Account saved successfully!");
            } else {
                System.out.println("Failed to save account.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }

    @Override
    public Account update(Account account) {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlUpdate)) {
            if (this.findById(account.getAccountId()) == null){
                throw new IllegalArgumentException("Account id not found!!");
            }

            statement.setString(1, account.getUsername());
            statement.setInt(2, account.getAccountId());
            int success = statement.executeUpdate();
            if (success > 0) {
                System.out.println("Account updated successfully!");
            } else {
                System.out.println("Failed to update account.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public void deleteById(Integer id) {
       try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlDelete);) {
            statement.setInt(1, id);
            int success = statement.executeUpdate();
            if (success > 0) {
                System.out.println("Account deleted successfully!");
            } else {
                System.out.println("Failed to delete account.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account findById(Integer id) {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlQueryById)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return AccountMapper.mapToAccount(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
