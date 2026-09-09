package com.tns.banking.app;

import java.util.Scanner;
import java.util.List;

import com.tns.banking.model.Customer;
import com.tns.banking.model.Account;
import com.tns.banking.model.Beneficiary;
import com.tns.banking.service.BankingService;
import com.tns.banking.service.BankingServiceImpl;

public class BankingSystemApp 
{

    public static void main(String[] args) 
    {

        Scanner sc = new Scanner(System.in);

        BankingService service = new BankingServiceImpl();
        
        while (true) {

        System.out.println("=================================");
        System.out.println("       BANKING SYSTEM");
        System.out.println("=================================");

        System.out.println("\n1. Add Customer");
        System.out.println("2. Find Customer");
        System.out.println("3. View All Customers");
        //account operation
        
        
        
        System.out.println("4. Add Account");
        System.out.println("5. Find Account");
        System.out.println("6. View All Accounts");
        System.out.println("7. Process Transaction");
        System.out.println("8. Transfer Money");
        System.out.println("9. Add Beneficiary");
        System.out.println("10. Find Beneficiary");
        System.out.println("11. View All Beneficiaries");
        System.out.println("12. Exit");
       
        
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) 
        {
          
        case 1:

            System.out.print("Enter Customer ID: ");
            int customerID = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Customer Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Address: ");
            String address = sc.nextLine();

            System.out.print("Enter Contact: ");
            String contact = sc.nextLine();

            Customer customer = new Customer(
                    customerID,
                    name,
                    address,
                    contact
            );

            service.addCustomer(customer);

            System.out.println("Customer added successfully!");

            break;

        case 2:

            System.out.print("Enter Customer ID: ");
            int customerId = sc.nextInt();

            Customer foundCustomer = service.findCustomerById(customerId);

            if (foundCustomer != null) 
            {
                System.out.println("Customer Found!");
                System.out.println(foundCustomer);
            } 
            else 
            {
                System.out.println("Customer not found.");
            }

            break;
            
        case 3:
            System.out.println("\n========== ALL CUSTOMERS ==========");

            List<Customer> customers = service.getAllCustomers();

            if (customers.isEmpty()) {
                System.out.println("No customers found.");
            } else {
                for (Customer c : customers) {
                    System.out.println(c);
                }
            }
            break;
            
            
        case 4:
            System.out.println("\n========== ADD ACCOUNT ==========");

            System.out.print("Enter Account ID: ");
            int accountId = sc.nextInt();

            System.out.print("Enter Customer ID: ");
            int accountcustomerId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Account Type: ");
            String type = sc.nextLine();

            System.out.print("Enter Initial Balance: ");
            double balance = sc.nextDouble();

            Account account = new Account(
                    accountId,
                    accountcustomerId,
                    type,
                    balance
            );

            service.addAccount(account);

            System.out.println("Account added successfully!");
            break;
            
        case 5:
            System.out.print("Enter Account ID: ");
            int findAccountId = sc.nextInt();

            Account foundAccount = service.findAccountById(findAccountId);

            if (foundAccount != null) {
                System.out.println("Account Found!");
                System.out.println(foundAccount);
            } else {
                System.out.println("Account not found.");
            }
            break;
            
            
        case 6:
            System.out.println("\n========== ALL ACCOUNTS ==========");

            List<Account> accounts = service.getAllAccounts();

            if (accounts.isEmpty()) {
                System.out.println("No accounts found.");
            } else {
                for (Account a : accounts) {
                    System.out.println(a);
                }
            }
            break;
            
        case 7:
            System.out.println("\n========== PROCESS TRANSACTION ==========");

            System.out.print("Enter Account ID: ");
            int transactionAccountId = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter Type (Deposit/Withdraw): ");
            String transactionType = sc.nextLine();

            System.out.print("Enter Amount: ");
            double transactionAmount = sc.nextDouble();

            service.processTransaction(
                    transactionAccountId,
                    transactionType,
                    transactionAmount
            );

            System.out.println("Transaction processed successfully!");
            break;
            
            
        case 8:
            System.out.println("\n========== TRANSFER MONEY ==========");

            System.out.print("Enter From Account ID: ");
            int fromAccountId = sc.nextInt();

            System.out.print("Enter To Account ID: ");
            int toAccountId = sc.nextInt();

            System.out.print("Enter Amount: ");
            double transferAmount = sc.nextDouble();

            service.transferMoney(
                    fromAccountId,
                    toAccountId,
                    transferAmount
            );

            System.out.println("Transfer completed!");
            break;
            
        case 9:
            System.out.println("\n========== ADD BENEFICIARY ==========");

            System.out.print("Enter Beneficiary ID: ");
            int beneficiaryID = sc.nextInt();

            System.out.print("Enter Customer ID: ");
            int beneficiaryCustomerID = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Beneficiary Name: ");
            String beneficiaryName = sc.nextLine();

            System.out.print("Enter Account Number: ");
            String accountNumber = sc.nextLine();

            System.out.print("Enter Bank Details: ");
            String bankDetails = sc.nextLine();

            Beneficiary beneficiary = new Beneficiary(
                    beneficiaryID,
                    beneficiaryCustomerID,
                    beneficiaryName,
                    accountNumber,
                    bankDetails
            );

            service.addBeneficiary(beneficiary);

            System.out.println("Beneficiary added successfully!");
            break;
            
        case 10:
            System.out.println("\n========== FIND BENEFICIARY ==========");

            System.out.print("Enter Beneficiary ID: ");
            int findBeneficiaryID = sc.nextInt();

            Beneficiary foundBeneficiary =
                    service.findBeneficiaryById(findBeneficiaryID);

            if (foundBeneficiary != null) {
                System.out.println("Beneficiary Found!");
                System.out.println(foundBeneficiary);
            } else {
                System.out.println("Beneficiary not found.");
            }

            break;
            
        case 11:
            System.out.println("\n========== ALL BENEFICIARIES ==========");

            List<Beneficiary> beneficiaries =
                    service.getAllBeneficiaries();

            if (beneficiaries.isEmpty()) {
                System.out.println("No beneficiaries found.");
            } else {
                for (Beneficiary b : beneficiaries) {
                    System.out.println(b);
                }
            }

            break;
            
            
            case 12:

            System.out.println("Thank you for using Banking System!");
            sc.close();
            
            return;
           
            
            default:
            	System.out.println("Invalid Choice!");
            
        
        }
     }
    
}
}