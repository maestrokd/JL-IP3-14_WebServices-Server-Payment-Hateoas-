package ua.com.univerpulse.model.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.univerpulse.model.entities.Customer;
import ua.com.univerpulse.model.entities.Payment;

import java.util.List;

/**
 * @author Danny Briskin (sql.coach.kiev@gmail.com)
  */
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("SELECT p FROM ua.com.univerpulse.model.entities.Payment p "
            + " WHERE p.customer.id = :id")
    List<Payment> getCustomerPayments(@Param("id") int id);

    @Query("SELECT p FROM ua.com.univerpulse.model.entities.Payment p "
            + " WHERE p.customer.id = :customerId and p.id = :id")
    Payment getCustomerPayment(@Param("id") int id, @Param("customerId") int customerId);

    Page<Payment> findByCustomer(Customer customer, Pageable pageable);

    Page<Payment> findAll(Pageable pageable);

}



