package com.dtn2603.repository;

import com.dtn2603.entity.Account;

import java.util.List;

public interface IAccountRepository {
    List<Account> findAll();
    Account findById(Integer id);
    boolean save(Account account);
    boolean update(Account account);
    boolean deleteById(Integer id);
}
