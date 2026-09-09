package com.tns.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.tns.banking.model.Transaction;
import com.tns.banking.util.DBConnection;

public class TransactionDAOImpl {

    public void addTransaction(Transaction transaction) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO transactions (account_id, type, amount, txn_timestamp) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, transaction.getAccountID());
            ps.setString(2, transaction.getType());
            ps.setDouble(3, transaction.getAmount());
            ps.setTimestamp(4, Timestamp.valueOf(transaction.getTimestamp()));
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Transaction> getTransactionsByAccountId(int accountId) {
        List<Transaction> list = new ArrayList<Transaction>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM transactions WHERE account_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction(rs.getInt("account_id"), rs.getString("type"), rs.getDouble("amount"));
                t.setTransactionID(rs.getInt("transaction_id"));
                t.setTimestamp(rs.getTimestamp("txn_timestamp").toLocalDateTime());
                list.add(t);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    public void processTransaction(int accountId, String type, double amount) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement selectPs = con.prepareStatement("SELECT balance FROM accounts WHERE account_id = ?");
            selectPs.setInt(1, accountId);
            ResultSet rs = selectPs.executeQuery();
            double currentBalance = 0;
            if (rs.next()) {
                currentBalance = rs.getDouble("balance");
            }
            rs.close();
            selectPs.close();

            if (type.equals("Withdrawal") && amount > currentBalance) {
                System.out.println("Insufficient balance.");
                con.close();
                return;
            }

            double newBalance = type.equals("Deposit") ? currentBalance + amount : currentBalance - amount;

            PreparedStatement updatePs = con.prepareStatement("UPDATE accounts SET balance = ? WHERE account_id = ?");
            updatePs.setDouble(1, newBalance);
            updatePs.setInt(2, accountId);
            updatePs.executeUpdate();
            updatePs.close();
            con.close();

            addTransaction(new Transaction(accountId, type, amount));

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void transferMoney(int fromAccountId, int toAccountId, double amount) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            PreparedStatement selectPs = con.prepareStatement("SELECT balance FROM accounts WHERE account_id = ?");
            selectPs.setInt(1, fromAccountId);
            ResultSet rs = selectPs.executeQuery();
            double fromBalance = 0;
            if (rs.next()) {
                fromBalance = rs.getDouble("balance");
            }
            rs.close();
            selectPs.close();

            if (amount > fromBalance) {
                System.out.println("Insufficient balance.");
                con.setAutoCommit(true);
                con.close();
                return;
            }

            PreparedStatement withdrawPs = con.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_id = ?");
            withdrawPs.setDouble(1, amount);
            withdrawPs.setInt(2, fromAccountId);
            withdrawPs.executeUpdate();
            withdrawPs.close();

            PreparedStatement depositPs = con.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_id = ?");
            depositPs.setDouble(1, amount);
            depositPs.setInt(2, toAccountId);
            depositPs.executeUpdate();
            depositPs.close();

            con.commit();
            con.setAutoCommit(true);
            con.close();

            addTransaction(new Transaction(fromAccountId, "Withdrawal", amount));
            addTransaction(new Transaction(toAccountId, "Deposit", amount));

        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Rollback error: " + ex.getMessage());
            }
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }
}