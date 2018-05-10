package ua.com.univerpulse.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.univerpulse.model.dto.PaymentDTO;
import ua.com.univerpulse.model.entities.Customer;
import ua.com.univerpulse.model.entities.Payment;
import ua.com.univerpulse.model.repositories.CustomerRepository;
import ua.com.univerpulse.model.repositories.PaymentRepository;
import ua.com.univerpulse.model.services.PaymentServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PaymentServiceTest {

    // Fields
    @Mock
    private PaymentRepository paymentRepositoryMock;
    @Mock
    private CustomerRepository customerRepositoryMock;

    private PaymentServiceImpl paymentServiceImpl;
    private PaymentDTO paymentDto101, paymentDto102;
    private Payment payment101;
    private Customer customer101, customer102;

    {

    }


    // Methods
    @Before
    public void setup() {

        customer101 = new Customer();
        customer101.setId(101);
        customer102 = new Customer();
        customer102.setId(102);

        paymentDto101 = new PaymentDTO(101, 101, 100F, "01-01-2018", "channel 101");
        paymentDto102 = new PaymentDTO(102, 102, 200F, "01-01-2018", "channel 102");

        payment101 = new Payment(customer101, 100F, "channel 101");
        payment101.setDateAsString("01-01-2018");
        payment101.setId(101);

        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment101);


        when(paymentRepositoryMock.findOne(101)).thenReturn(payment101);
        when(paymentRepositoryMock.findOne(102)).thenReturn(null);

        when(paymentRepositoryMock.getCustomerPayments(101)).thenReturn(paymentList);
        when(paymentRepositoryMock.getCustomerPayments(102)).thenReturn(new ArrayList<>());

        when(paymentRepositoryMock.saveAndFlush(payment101)).thenReturn(payment101);

        when(customerRepositoryMock.findOne(101)).thenReturn(customer101);
        when(customerRepositoryMock.findOne(102)).thenReturn(null);



        paymentServiceImpl = new PaymentServiceImpl();
        paymentServiceImpl.setCustomerRepository(customerRepositoryMock);
        paymentServiceImpl.setPaymentRepository(paymentRepositoryMock);
    }


    @Test
    public void getPayment_thenNotNullAndPayment() {
        assertNotNull(paymentServiceImpl.getPayment(101));
        assertEquals(payment101, paymentServiceImpl.getPayment(101));
    }


    @Test
    public void getPayment_thenNull() {
        assertNull(paymentServiceImpl.getPayment(102));
    }


    @Test
    public void getCustomerPayments_thenNotNullAndPaymentsSizeEquals1() {
        assertNotNull(paymentServiceImpl.getCustomerPayments(customer101));
        assertEquals(1, paymentServiceImpl.getCustomerPayments(customer101).size());
    }

    @Test
    public void getCustomerPayments_thenNotNullAndPaymentsSizeEquals0() {
        assertNotNull(paymentServiceImpl.getCustomerPayments(customer102));
        assertEquals(0, paymentServiceImpl.getCustomerPayments(customer102).size());
    }

    @Test
    public void createPayment_thenNotNullAndEqualsPayment() {
        assertNotNull(paymentServiceImpl.create(paymentDto101));
        assertEquals(payment101, paymentServiceImpl.create(paymentDto101));
    }


    @Test
    public void createPayment_thenNull() {
        assertNull(paymentServiceImpl.create(paymentDto102));
    }

}
