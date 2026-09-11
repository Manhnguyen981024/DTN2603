package backend.service;

import java.util.Comparator;
import java.util.List;

import backend.repository.IQLAccount;
import backend.repository.IQLDepartment;
import backend.repository.IQLPosition;
import entity.Account;

public class AccountService {
    private final IQLAccount qlAccount;
    private final IQLDepartment qlDepartment;
    private final IQLPosition qlPosition;

    public AccountService(IQLAccount qlAccount, IQLDepartment qlDepartment, IQLPosition qlPosition) {
        this.qlAccount = qlAccount;
        this.qlDepartment = qlDepartment;
        this.qlPosition = qlPosition;
    }

    public List<Account> findAll() {
        return qlAccount.findAll()
                        .stream()
                        .sorted(Comparator.comparing(Account::getAccountId))
                        .toList();
    }

    public Account createAccount(Account account) {
        validateAccount(account);

        if (account.getDepartment() != null 
                && qlDepartment.findById(account.getDepartment().getDepartmentId()) == null) {
            account.setDepartment(null);

        }

        if (account.getPosition() != null 
                && qlPosition.findById(account.getPosition().getPositionId()) == null) {
            account.setPosition(null);
        }

        return qlAccount.save(account);
    }

    public Account updateAccount(Account account) {
        if (account.getAccountId() <= 0) {
            throw new IllegalArgumentException("Account ID must be greater than 0");
        }

        if (account.getUsername() == null || account.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        return qlAccount.update(account);
    }

    public void deleteAccountById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Account ID must be greater than 0");
        }
        qlAccount.deleteById(id);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private void validateAccount(Account account) {
        if (account.getEmail() == null || account.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (account.getUsername() == null || account.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (account.getFullName() == null || account.getFullName().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be null or empty");
        }

        if (!isValidEmail(account.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
