package com.tns.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tns.banking.model.Beneficiary;
import com.tns.banking.util.DBConnection;

public class BeneficiaryDAOImpl implements BeneficiaryDAO {

    @Override
    public void addBeneficiary(Beneficiary beneficiary) {

        String sql = "INSERT INTO beneficiaries " +
                     "(beneficiary_id, customer_id, name, account_number, bank_details) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, beneficiary.getBeneficiaryID());
            ps.setInt(2, beneficiary.getCustomerID());
            ps.setString(3, beneficiary.getName());
            ps.setString(4, beneficiary.getAccountNumber());
            ps.setString(5, beneficiary.getBankDetails());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding beneficiary", e);
        }
    }

    @Override
    public Beneficiary findBeneficiaryById(int beneficiaryId) {

        String sql = "SELECT * FROM beneficiaries WHERE beneficiary_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, beneficiaryId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Beneficiary(
                    rs.getInt("beneficiary_id"),
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("account_number"),
                    rs.getString("bank_details")
                );
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding beneficiary", e);
        }
    }

    @Override
    public List<Beneficiary> getAllBeneficiaries() {

        List<Beneficiary> beneficiaries = new ArrayList<>();

        String sql = "SELECT * FROM beneficiaries";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Beneficiary beneficiary = new Beneficiary(
                    rs.getInt("beneficiary_id"),
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("account_number"),
                    rs.getString("bank_details")
                );

                beneficiaries.add(beneficiary);
            }

            return beneficiaries;

        } catch (SQLException e) {
            throw new RuntimeException("Error getting all beneficiaries", e);
        }
    }

    @Override
    public List<Beneficiary> getBeneficiariesByCustomerId(int customerId) {

        List<Beneficiary> beneficiaries = new ArrayList<>();

        String sql = "SELECT * FROM beneficiaries WHERE customer_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Beneficiary beneficiary = new Beneficiary(
                    rs.getInt("beneficiary_id"),
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("account_number"),
                    rs.getString("bank_details")
                );

                beneficiaries.add(beneficiary);
            }

            return beneficiaries;

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error getting beneficiaries by customer ID", e);
        }
    }
}