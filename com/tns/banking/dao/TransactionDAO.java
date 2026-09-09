package com.tns.banking.dao;

import java.util.List;
import com.tns.banking.model.Transaction;

public interface TransactionDAO {
    void addTransaction(Transaction transaction);
    List<Transaction> getTransactionsByAccountId(int accountId);
    void processTransaction(int accountId, String type, double amount);
    void transferMoney(int fromAccountId, int toAccountId, double amount);
}