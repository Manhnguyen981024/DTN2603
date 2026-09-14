package com.dtn2603.service.impl;

import com.dtn2603.entity.Account;
import com.dtn2603.exception.ResourceNotFoundException;
import com.dtn2603.repository.IAccountRepository;
import com.dtn2603.service.IAccountService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final IAccountRepository accountRepository;

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public boolean addAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public boolean updateAccount(Account account) {
        Account updatedAccount = this.accountRepository.findById(account.getAccountId());
        updatedAccount.setUsername(account.getUsername());
        return accountRepository.update(updatedAccount);
    }

    @Override
    public boolean deleteAccountById(Integer id) {
        return accountRepository.deleteById(id);
    }
}
