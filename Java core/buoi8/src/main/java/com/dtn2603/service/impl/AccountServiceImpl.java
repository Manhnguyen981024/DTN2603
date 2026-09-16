package com.dtn2603.service.impl;

import com.dtn2603.entity.Account;
import com.dtn2603.exception.DuplicateAccountException;
import com.dtn2603.exception.ResourceNotFoundException;
import com.dtn2603.repository.IAccountRepository;
import com.dtn2603.service.IAccountService;
import com.dtn2603.service.IDepartmentService;
import com.dtn2603.service.IPositionService;
import com.dtn2603.utils.ValidationUtils;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final int MIN_LENGTH = 6;
    private final int MAX_LENGTH = 100;
    private final IAccountRepository accountRepository;
    private final IDepartmentService departmentService;
    private final IPositionService positionService;

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public boolean addAccount(Account account) {
        if (Objects.isNull(account)) {
            throw new IllegalArgumentException("Account can't be null");
        }

        if (!ValidationUtils.isValidLength(account.getUsername(), MIN_LENGTH, MAX_LENGTH)) {
            throw new IllegalArgumentException(String.format("Account username length must from %d to %d", MIN_LENGTH, MAX_LENGTH));
        }

        if (!ValidationUtils.isValidLength(account.getEmail(), MIN_LENGTH, MAX_LENGTH)) {
            throw new IllegalArgumentException(String.format("Account email length must from %d to %d", MIN_LENGTH, MAX_LENGTH));
        }

        if (!ValidationUtils.isValidEmail(account.getEmail())) {
            throw new IllegalArgumentException("Account email is not valid!");
        }

        if (!ValidationUtils.isValidLength(account.getFullName(), MIN_LENGTH, MAX_LENGTH)) {
            throw new IllegalArgumentException(String.format("Account fullname length must from %d to %d", MIN_LENGTH, MAX_LENGTH));
        }

        if (Objects.nonNull(accountRepository.findByUsername(account.getUsername()))) {
            throw new DuplicateAccountException("Account username already exist");
        }

        if (Objects.nonNull(accountRepository.findByEmail(account.getEmail()))) {
            throw new DuplicateAccountException("Account email already exist");
        }

        if (Objects.nonNull(account.getDepartment())
                && Objects.isNull(departmentService.getDepartmentById(account.getDepartment().getDepartmentId()))){
            throw new ResourceNotFoundException("Department don't exist");
        }

        if (Objects.nonNull(account.getPosition())
            && Objects.isNull(positionService.getPositionById(account.getPosition().getPositionId()))){
            throw new ResourceNotFoundException("Position don't exist");
        }

        return accountRepository.save(account);
    }

    @Override
    public boolean updateAccount(Account account) {
         if (Objects.isNull(account)) {
             throw new IllegalArgumentException("Account can't be null");
         }

        if (!ValidationUtils.isValidLength(account.getUsername(), MIN_LENGTH, MAX_LENGTH)) {
            throw new IllegalArgumentException(String.format("Account username length must from %d to %d", MIN_LENGTH, MAX_LENGTH));
        }

        if (account.getAccountId() <= 0) {
            throw new IllegalArgumentException("Account id must be positive");
        }

        if (Objects.isNull(accountRepository.findById(account.getAccountId()))) {
            throw new ResourceNotFoundException("Account id not found");
        }

        if (Objects.nonNull(accountRepository.findByUsername(account.getUsername()))) {
            throw new IllegalArgumentException("Account username already exist");
        }
        return accountRepository.update(account);
    }

    @Override
    public boolean deleteAccountById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Account id must be positive");
        }

        if (Objects.isNull(accountRepository.findById(id))) {
            throw new ResourceNotFoundException("Account id not found");
        }
        return accountRepository.deleteById(id);
    }
}
