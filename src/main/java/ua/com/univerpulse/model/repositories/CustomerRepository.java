package ua.com.univerpulse.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.univerpulse.model.entities.Customer;

import java.util.List;

/**
 * @author Danny Briskin (sql.coach.kiev@gmail.com)
  */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

//    List<Customer> findCustomersByPayment

}
