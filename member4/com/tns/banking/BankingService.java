package com.tns.banking.service;

import java.util.List;

import com.tns.banking.model.Customer;
import com.tns.banking.model.Account;
import com.tns.banking.model.Transaction;
import com.tns.banking.model.Beneficiary;

public interface BankingService {

    // Add operations
    void addCustomer(Customer customer);

    void addAccount(Account account);

    void addTransaction(Transaction transaction);

    void addBeneficiary(Beneficiary beneficiary);

    // Find operations
    Customer findCustomerById(int customerId);

    Account findAccountById(int accountId);

    

    Beneficiary findBeneficiaryById(int beneficiaryId);

    // Get all records
    List<Customer> getAllCustomers();

    List<Account> getAllAccounts();

    
    List<Beneficiary> getAllBeneficiaries();

    // Relationship-based operations
    List<Account> getAccountsByCustomerId(int customerId);

    List<Transaction> getTransactionsByAccountId(int accountId);

    List<Beneficiary> getBeneficiariesByCustomerId(int customerId);

 // Money transfer and transaction operations

    void processTransaction(int accountId, String type, double amount);

    void transferMoney(int fromAccountId, int toAccountId, double amount);
}