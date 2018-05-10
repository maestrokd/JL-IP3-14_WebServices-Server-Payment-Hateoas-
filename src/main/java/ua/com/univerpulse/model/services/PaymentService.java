package ua.com.univerpulse.model.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.univerpulse.model.dto.PaymentDTO;
import ua.com.univerpulse.model.entities.Customer;
import ua.com.univerpulse.model.entities.Payment;

import java.util.List;

/**
 * @author Danny Briskin (sql.coach.kiev@gmail.com)
  */
public interface PaymentService {
    Payment addPayment(Payment payment);
    Payment makePayment(Customer customer, PaymentDTO payment);
    Integer makePayment(Customer customer, Payment payment);

    Payment getPayment(int id);

    List<Payment> getAllPayments();
    List<Payment> getCustomerPayments(Customer customer);

    Payment create(PaymentDTO resource);

    void updatePayment(Payment payment,PaymentDTO paymentDTO);
    void updatePayment(Payment payment,Payment  newPayment);

    void deletePayment(int id);

    Page<Payment> findAll(Pageable pageable);

    Integer create(Payment payment);
}
