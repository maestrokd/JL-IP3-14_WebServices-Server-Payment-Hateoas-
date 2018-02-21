package ua.com.univerpulse.model.services;

import ua.com.univerpulse.model.entities.Customer;

import java.util.List;

/**
 * @author Danny Briskin (sql.coach.kiev@gmail.com)
  */
public interface CustomerService {
    public Customer getCustomer(int id);

    public List<Customer> getAllCustomers();

}
