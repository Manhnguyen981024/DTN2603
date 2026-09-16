package com.dtn2603.controller;

import com.dtn2603.entity.Account;
import com.dtn2603.exception.DataAccessException;
import com.dtn2603.exception.DuplicateAccountException;
import com.dtn2603.exception.ResourceNotFoundException;
import com.dtn2603.service.IAccountService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AccountController {
    private final IAccountService accountService;

    public List<Account> getAllAccount() {
        try {
            return accountService.getAllAccount();
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
        }
        return List.of();
    }

    public boolean addAccount(Account account) {
        try {
            return accountService.addAccount(account);
        } catch (IllegalArgumentException
                 | ResourceNotFoundException
                 | DuplicateAccountException
                 | DataAccessException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteAccount(Integer id) {
        try {
            return accountService.deleteAccountById(id);
        } catch (IllegalArgumentException
                 | ResourceNotFoundException
                 | DuplicateAccountException
                 | DataAccessException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean updateAccount(Account account) {
        try {
            return accountService.updateAccount(account);
        } catch (IllegalArgumentException
                 | ResourceNotFoundException
                 | DuplicateAccountException
                 | DataAccessException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
