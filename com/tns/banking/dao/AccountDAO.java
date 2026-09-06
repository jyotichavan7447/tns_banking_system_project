package com.tns.banking.dao;

import com.tns.banking.model.Account;
import java.util.List;

public interface AccountDAO {
    void addAccount(Account account);
    Account findAccountById(int accountId);
    List<Account> getAllAccounts();
    List<Account> getAccountsByCustomerId(int customerId);
    void updateBalance(int accountId, double newBalance);
}