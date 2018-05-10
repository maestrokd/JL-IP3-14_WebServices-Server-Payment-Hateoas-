package ua.com.univerpulse.model.services;

import ua.com.univerpulse.model.entities.Customer;

import java.util.List;


public interface CustomerService {

    Customer getCustomer(int id);

    List<Customer> getAllCustomers();

    Customer sumCustomerBalanceWithPaymentAmount(int id, float amount);

}
