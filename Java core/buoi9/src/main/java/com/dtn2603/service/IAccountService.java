package com.dtn2603.service;

import com.dtn2603.entity.Account;

import java.util.List;
import java.util.Map;

public interface IAccountService {
    List<Account> getAllAccount();
    boolean addAccount(Account account);
    boolean updateAccount(Account account);
    boolean deleteAccountById(Integer id);
    Map<String, Integer> importAccountFromCSV(String filename);
}
