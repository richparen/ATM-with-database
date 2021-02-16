package com.atm.atm.Center;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Transactions")
public class Transaction {

    @Id @GeneratedValue (strategy=GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private int transactionId;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "transaction_time", nullable = false)
    private Timestamp time;
    @Column(name = "account_no", nullable = false)
    private int accountNo;
    @Column(name = "amount", nullable = false)
    private float amount;

    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
    public int getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }
    public int getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }
}