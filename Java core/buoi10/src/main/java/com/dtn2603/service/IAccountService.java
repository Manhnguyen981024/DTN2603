package com.dtn2603.service;

import com.dtn2603.entity.Account;

import java.util.List;
import java.util.Map;

public interface IAccountService extends ICsvImportService<Account> {
    List<Account> getAllAccount();
    boolean addAccount(Account account);
    boolean updateAccount(Account account);
    boolean deleteAccountById(Integer id);

    default Map<String, Integer> importAccountFromCSV(String filename) {
        return importCSV(filename);
    }
}