package ua.com.univerpulse.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.univerpulse.model.entities.Customer;
import ua.com.univerpulse.model.repositories.CustomerRepository;

import java.util.List;

/**
 * @author Danny Briskin (sql.coach.kiev@gmail.com)
  */
@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public Customer getCustomer(int id) {
        return customerRepository.findOne(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer sumCustomerBalanceWithPaymentAmount(int id, float amount) {
        Customer customer = customerRepository.findOne(id);
        float balance = customer.getBalance();
        balance += amount;
        customer.setBalance(balance);
        return customerRepository.saveAndFlush(customer);
    }
}
