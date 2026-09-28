package com.dtn2603.repository;

import com.dtn2603.entity.Account;

import java.util.List;
import java.util.Set;

public interface IAccountRepository {
    List<Account> findAll();
    Account findById(Integer id);
    Account findByUsername(String username);
    Account findByEmail(String email);
    Set<String> findExistingUsernames(List<String> usernames);
    Set<String> findExistingEmails(List<String> emails);
    boolean save(Account account);
    boolean update(Account account);
    boolean deleteById(Integer id);
    int saveAll(List<Account> accounts);
}
