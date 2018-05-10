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

import java.util.Date;
import java.util.List;

@Service(value = "paymentService")
public class PaymentServiceImpl implements PaymentService {

    // Fields
    private PaymentRepository paymentRepository;
    private CustomerRepository customerRepository;


    // Getters and Setters
    @Autowired
    public void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    // Methods
    @Override
    public Page<Payment> findAll(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }


    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }


    public Payment getPayment(int id) {
        Payment payment = paymentRepository.findOne(id);
        return payment;
    }


    @Override
    public List<Payment> getCustomerPayments(Customer customer) {
        return paymentRepository.getCustomerPayments(customer.getId());
    }


    @Override
    public Integer create(Payment payment) {
        // TODO stub to create payment without customer
        Customer customer = customerRepository.findOne(1);
        return this.makePayment(customer, payment);
    }


    @Override
    public Payment create(PaymentDTO resource) {
        Customer customer = customerRepository.findOne(resource.getCustomerId());
        return this.makePayment(customer, resource);
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
    public void deletePayment(int id) {
        paymentRepository.delete(id);
        paymentRepository.flush();
    }


    public Payment addPayment(Payment payment) {
        return paymentRepository.saveAndFlush(payment);
    }


    @Override
    public Payment makePayment(Customer customer, PaymentDTO paymentDTO) {
        if (customer == null) {
            return null;
        } else {
            Payment payment = new Payment();
            payment.setCustomer(customer);
            payment.setAmount(paymentDTO.getAmount());
//            payment.setDateAsString(paymentDTO.getDate());
//            payment.setDate(new Date());
//            payment.setDate(new Date());
            payment.setChannel(paymentDTO.getChannel());
            return this.addPayment(payment);
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


}
