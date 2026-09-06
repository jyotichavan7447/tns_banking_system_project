package com.tns.banking.dao;

import java.util.List;
import com.tns.banking.model.Customer;

public interface CustomerDAO {

	void addCustomer(Customer customer);

	Customer findCustomerById(int customerId);

	List<Customer> getAllCustomers();

}
