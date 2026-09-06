
package com.tns.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tns.banking.model.Customer;
import com.tns.banking.util.DBConnection;

public class CustomerDAOImpl implements CustomerDAO 
{

    @Override
    public void addCustomer(Customer customer) {

        String sql = "INSERT INTO customers " +
                     "(customer_id, name, address, contact) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customer.getCustomerID());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getContact());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding customer", e);
        }
    }

    @Override
    public Customer findCustomerById(int customerId) {

        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("contact")
                );
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding customer", e);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {

        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT * FROM customers";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Customer customer = new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("contact")
                );

                customers.add(customer);
            }

            return customers;

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error getting all customers", e);
        }
    }

}
