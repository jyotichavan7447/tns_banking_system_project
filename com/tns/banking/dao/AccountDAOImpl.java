package com.tns.banking.dao;

import com.tns.banking.model.Account;
import com.tns.banking.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public void addAccount(Account account) {
        String sql = "INSERT INTO accounts (account_id, customer_id, type, balance) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, account.getAccountId());
            stmt.setInt(2, account.getCustomerId());
            stmt.setString(3, account.getType());
            stmt.setDouble(4, account.getBalance());
            
            stmt.executeUpdate();
            System.out.println("Account added successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Error adding account: " + e.getMessage(), e);
        }
    }

    @Override
    public Account findAccountById(int accountId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                        rs.getInt("account_id"),
                        rs.getInt("customer_id"),
                        rs.getString("type"),
                        rs.getDouble("balance")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding account by ID: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                accounts.add(new Account(
                    rs.getInt("account_id"),
                    rs.getInt("customer_id"),
                    rs.getString("type"),
                    rs.getDouble("balance")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all accounts: " + e.getMessage(), e);
        }
        return accounts;
    }

    @Override
    public List<Account> getAccountsByCustomerId(int customerId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    accounts.add(new Account(
                        rs.getInt("account_id"),
                        rs.getInt("customer_id"),
                        rs.getString("type"),
                        rs.getDouble("balance")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting accounts by customer ID: " + e.getMessage(), e);
        }
        return accounts;
    }

    @Override
    public void updateBalance(int accountId, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, newBalance);
            stmt.setInt(2, accountId);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating balance: " + e.getMessage(), e);
        }
    }
}