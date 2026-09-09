package com.tns.banking.service;

import java.util.List;

import com.tns.banking.dao.CustomerDAO;
import com.tns.banking.dao.CustomerDAOImpl;
import com.tns.banking.dao.AccountDAO;
import com.tns.banking.dao.AccountDAOImpl;

import com.tns.banking.dao.TransactionDAOImpl;
import com.tns.banking.dao.BeneficiaryDAO;
import com.tns.banking.dao.BeneficiaryDAOImpl;

import com.tns.banking.model.Customer;
import com.tns.banking.model.Account;
import com.tns.banking.model.Transaction;
import com.tns.banking.model.Beneficiary;

public class BankingServiceImpl implements BankingService {

    private CustomerDAO customerDAO;
    private AccountDAO accountDAO;
    private TransactionDAOImpl transactionDAO;
    private BeneficiaryDAO beneficiaryDAO;

    public BankingServiceImpl() {
        customerDAO = new CustomerDAOImpl();
        accountDAO = new AccountDAOImpl();
        transactionDAO = new TransactionDAOImpl();
        beneficiaryDAO = new BeneficiaryDAOImpl();
    }

    // Customer
    @Override
    public void addCustomer(Customer customer) {
        customerDAO.addCustomer(customer);
    }

    @Override
    public Customer findCustomerById(int customerId) {
        return customerDAO.findCustomerById(customerId);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    // Account
    @Override
    public void addAccount(Account account) {
        accountDAO.addAccount(account);
    }

    @Override
    public Account findAccountById(int accountId) {
        return accountDAO.findAccountById(accountId);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    @Override
    public List<Account> getAccountsByCustomerId(int customerId) {
        return accountDAO.getAccountsByCustomerId(customerId);
    }

    // Transaction
    @Override
    public void addTransaction(Transaction transaction) {
        transactionDAO.addTransaction(transaction);
    }

   

   

    @Override
    public List<Transaction> getTransactionsByAccountId(int accountId) {
        return transactionDAO.getTransactionsByAccountId(accountId);
    }
    
    @Override
    public void processTransaction(int accountId, String type, double amount) {
        transactionDAO.processTransaction(accountId, type, amount);
    }

    // Beneficiary
    @Override
    public void addBeneficiary(Beneficiary beneficiary) {
        beneficiaryDAO.addBeneficiary(beneficiary);
    }

    @Override
    public Beneficiary findBeneficiaryById(int beneficiaryId) {
        return beneficiaryDAO.findBeneficiaryById(beneficiaryId);
    }

    @Override
    public List<Beneficiary> getAllBeneficiaries() {
        return beneficiaryDAO.getAllBeneficiaries();
    }

    @Override
    public List<Beneficiary> getBeneficiariesByCustomerId(int customerId) {
        return beneficiaryDAO.getBeneficiariesByCustomerId(customerId);
    }

    // Money transfer
    
    @Override
    public void transferMoney(int fromAccountId, int toAccountId, double amount) {

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        transactionDAO.transferMoney(fromAccountId, toAccountId, amount);
    }
}