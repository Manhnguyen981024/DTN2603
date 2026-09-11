package backend.repository;

import java.util.List;
import entity.Account;

public interface IQLAccount {
    List<Account> findAll();
    Account findById(Integer id);
    Account save(Account account);
    Account update(Account account);
    void deleteById(Integer id);
}
