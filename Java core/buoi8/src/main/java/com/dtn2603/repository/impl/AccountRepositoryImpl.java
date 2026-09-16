package com.dtn2603.repository.impl;

import com.dtn2603.entity.Account;
import com.dtn2603.exception.DataAccessException;
import com.dtn2603.exception.DuplicateAccountException;
import com.dtn2603.mapper.AccountMapper;
import com.dtn2603.repository.IAccountRepository;
import com.dtn2603.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements IAccountRepository {
    private final String sqlQuery = """
                            SELECT *
                            FROM account             AS acc
                                LEFT JOIN department AS dep
                                    ON  acc.department_id = dep.department_id
                                LEFT JOIN `position` AS p
                                    ON  acc.position_id = p.position_id
                                WHERE 1 = 1 
                        """;

    private final String sqlQueryByID = sqlQuery + " and id = ?";

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
                            ) 
                        """;

    private final String sqlUpdate = """
                            UPDATE account
                            SET username = ?
                            WHERE account_id = ? 
                        """ ;

    private final String sqlDelete = """
                            DELETE FROM account
                            WHERE account_id = ? 
                        """ ;

    private final String sqlQueryByUsername = sqlQuery + " and username = ?"; ;

    private final String sqlQueryByEmail = sqlQuery + " and email = ?" ;

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlQuery)) {

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    accounts.add(AccountMapper.mapToAccount(rs));
                }
            }
        } catch (SQLException e) {
            this.resolveSQLException(e);
        }
        return accounts;
    }

    @Override
    public Account findById(Integer id) {
        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlQueryByID)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return AccountMapper.mapToAccount(rs);
                }
            }
        } catch (SQLException e) {
            this.resolveSQLException(e);
        }
        return null;
    }

    @Override
    public Account findByUsername(String username) {
        try(Connection conn = JdbcUtils.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQueryByUsername)) {

            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return AccountMapper.mapToAccount(rs);
                }
            }
        } catch (SQLException e) {
            this.resolveSQLException(e);
        }
        return null;
    }

    @Override
    public Account findByEmail(String email) {
        try(Connection conn = JdbcUtils.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQueryByEmail)) {

            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return AccountMapper.mapToAccount(rs);
                }
            }
        } catch (SQLException e) {
            this.resolveSQLException(e);
        }
        return null;
    }

    @Override
    public boolean save(Account account) {
        try (Connection conn = JdbcUtils.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlInsert)) {
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

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
           this.resolveSQLException(e);
        }
        return false;
    }

    @Override
    public boolean update(Account account) {
        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlUpdate)) {
            statement.setString(1, account.getUsername());
            statement.setInt(2, account.getAccountId());
          return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            this.resolveSQLException(e);
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlDelete);) {
            statement.setInt(1, id);
           return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            this.resolveSQLException(e);
        }
        return false;
    }

    private void resolveSQLException(SQLException e){
        if (e.getErrorCode() == 1062) {
            throw new DuplicateAccountException(
                    "Email or username is already exist!", e
            );
        }
        throw new DataAccessException("Cannot access to database, please try again! caused by: " + e.getMessage(), e);
    }
}
