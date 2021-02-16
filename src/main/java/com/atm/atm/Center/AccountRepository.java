package com.atm.atm.Center;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer>{

    Account findByAccountNo(int accountNo);
    List<Account> findAll();
    Account save(Account a);
    Account deleteById(int accNum);
}
