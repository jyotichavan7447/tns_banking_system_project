package com.tns.banking.dao;

import java.util.List;

import com.tns.banking.model.Beneficiary;

public interface BeneficiaryDAO {

    void addBeneficiary(Beneficiary beneficiary);

    Beneficiary findBeneficiaryById(int beneficiaryId);

    List<Beneficiary> getAllBeneficiaries();

    List<Beneficiary> getBeneficiariesByCustomerId(int customerId);
}