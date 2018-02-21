package ua.com.univerpulse.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.univerpulse.model.dto.PaymentDTO;
import ua.com.univerpulse.model.entities.Customer;
import ua.com.univerpulse.model.entities.Payment;
import ua.com.univerpulse.model.repositories.CustomerRepository;
import ua.com.univerpulse.model.repositories.PaymentRepository;

import java.util.List;

/**
 * @author Danny Briskin (sql.coach.kiev@gmail.com)
  */
@Service(value = "paymentService")
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CustomerRepository customerRepository;


    public void addPayment(Payment payment) {

        paymentRepository.saveAndFlush(payment);
    }

    @Override
    public Integer makePayment(Customer customer, PaymentDTO paymentDTO) {
        if (customer == null) {
            return -1;
        } else {
            Payment payment = new Payment();
            payment.setCustomer(customer);
            payment.setAmount(paymentDTO.getAmount());
            payment.setDateAsString(paymentDTO.getDate());
            payment.setChannel(paymentDTO.getChannel());

            this.addPayment(payment);
            return payment.getId();
        }
    }

    @Override
    public Integer makePayment(Customer customer, Payment payment) {
        if (customer == null) {
            return -1;
        } else {
            Payment newPayment = new Payment();
            newPayment.setId(payment.getId());
            newPayment.setCustomer(customer);
            newPayment.setAmount(payment.getAmount());
            newPayment.setDate(payment.getDate());
            newPayment.setChannel(payment.getChannel());

            this.addPayment(newPayment);


            return newPayment.getId();
        }
    }

    public Payment getPayment(int id) {
        return paymentRepository.findOne(id);

    }

    @Override
    public Payment getPayment(int id, int customerID) {
        return paymentRepository.getCustomerPayment(id, customerID);
    }

    @Override
    public List<Payment> getAllPayments() {

        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> getCustomerPayments(Customer customer) {
        return paymentRepository.getCustomerPayments(customer.getId());
    }

    @Override
    public Integer create(PaymentDTO resource) {
        Customer customer = customerRepository.findOne(resource.getCustomerId());
        return this.makePayment(customer, resource);
    }

    @Override
    public void updatePayment(Payment payment, PaymentDTO paymentDTO) {
        if (payment != null) {
            payment.setAmount(paymentDTO.getAmount());
            payment.setDateAsString(paymentDTO.getDate());
            payment.setChannel(paymentDTO.getChannel());
            Customer customer = customerRepository.findOne(paymentDTO.getCustomerId());
            if (customer != null) {
                payment.setCustomer(customer);
            }
            paymentRepository.saveAndFlush(payment);
        }
    }

    @Override
    public void updatePayment(Payment payment, Payment newPayment) {
        if (payment != null) {
            payment.setAmount(newPayment.getAmount());
            payment.setDate(newPayment.getDate());
            payment.setChannel(newPayment.getChannel());
            Customer customer = newPayment.getCustomer();
            if (customer != null) {
                payment.setCustomer(customer);
            }
            paymentRepository.saveAndFlush(payment);
        }
    }

    @Override
    public void deletePayment(int id) {
        paymentRepository.delete(id);
        paymentRepository.flush();
    }

    @Override
    public Page<Payment> findAll(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    @Override
    public Integer create(Payment payment) {
        // TODO stub to create payment without customer
        Customer customer = customerRepository.findOne(1);
        return this.makePayment(customer, payment);

    }


}
