package com.atm.atm.service;

import com.atm.atm.Center.Transaction;
import com.atm.atm.Center.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBConnection {
    @Autowired
    TransactionRepository transactionRepository;

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }
    public Transaction getTransaction() {
        Transaction t = transactionRepository.findTopByOrderByTransactionIdDesc();
        return t;
    }
}
