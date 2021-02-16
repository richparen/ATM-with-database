package com.atm.atm.service;

import com.atm.atm.Center.Account;
import com.atm.atm.Center.AccountRepository;
import com.atm.atm.Center.Transaction;
import com.atm.atm.Center.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;


    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(account -> accounts.add(account));
        return accounts;
    }

    public Account getAccountByAccountNo(int accNo) {
        Account account = accountRepository.findByAccountNo(accNo);
        return account;
    }

    public void saveOrUpdate(Account account) {
        accountRepository.save(account);
    }


    public int checkLogin(Account formAccount) {
        Account account = getAccountByAccountNo(formAccount.getAccountNo());

        if (account == null) {
            return 1;
        } else if (account.getLocked() == 1) {
            return 3;
        } else if (account.getPin() != formAccount.getPin()) {
                if (account.getIncorrectAttempts() < 2) {
                    account.setIncorrectAttempts(account.getIncorrectAttempts() + 1);
                    saveOrUpdate(account);
                    return 2;
                } else {
                    account.setIncorrectAttempts(account.getIncorrectAttempts() + 1);
                    account.setLocked(1);
                    saveOrUpdate(account);
                    return 3;
                }
        } else {
            return 0;
        }

    }


    public boolean checkIfEnough(Account account, float amount) {
        float remaining = account.getBalance() - amount;
        if (remaining >= 0) {
            return true;
        }
        return false;
    }

    public boolean withdrawMoney(int accountNo, float amount) {
        Account account = getAccountByAccountNo(accountNo);
        if (checkIfEnough(account, amount)) {
            account.setBalance(account.getBalance() - amount);
            saveOrUpdate(account);
            //save transaction to database
            Transaction transaction = new Transaction();
            transaction.setAccountNo(accountNo);
            transaction.setTime(new Timestamp(new Date().getTime()));
            transaction.setType("withdraw");
            transaction.setAmount(amount);
            transactionRepository.save(transaction);
            return true;
        }
        return false;
    }


    public boolean depositMoney(int accountNo, float amount) {
        Account account = getAccountByAccountNo(accountNo);
        account.setBalance(account.getBalance() + amount);
        saveOrUpdate(account);
        Transaction transaction = new Transaction();
        transaction.setAccountNo(accountNo);
        transaction.setTime(new Timestamp(new Date().getTime()));
        transaction.setType("deposit");
        transaction.setAmount(amount);
        transactionRepository.save(transaction);
        return true;
    }

    public float getBalance(int accountNum) {
        Account account = getAccountByAccountNo(accountNum);
        return account.getBalance();
    }

}
