package backend.repository;

import java.util.List;
import entity.Account;

public interface IQLAccount {
    List<Account> findAll();
}
