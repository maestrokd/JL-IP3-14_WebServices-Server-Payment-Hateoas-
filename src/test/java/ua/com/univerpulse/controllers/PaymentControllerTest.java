package ua.com.univerpulse.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ua.com.univerpulse.model.dto.PaymentDTO;
import ua.com.univerpulse.model.entities.Customer;
import ua.com.univerpulse.model.entities.Payment;
import ua.com.univerpulse.model.services.CustomerService;
import ua.com.univerpulse.model.services.PaymentService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import ua.com.univerpulse.springtestingmocks.model.entity.User;
//import ua.com.univerpulse.springtestingmocks.model.repository.UserRepository;


@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentServiceMock;
    @MockBean
    private CustomerService customerServiceMock;

    private PaymentDTO paymentDto101, paymentDto102;


    @Before
    public void setup() {
        Customer customer = new Customer();
        customer.setId(101);

        paymentDto101 = new PaymentDTO(101, 101, 100F, "01-01-2018", "channel 101");
        paymentDto102 = new PaymentDTO(102, 102, 200F, "01-01-2018", "channel 102");

        Payment payment101 = new Payment(customer, 100F, "channel 101");
        payment101.setDateAsString("01-01-2018");
        payment101.setId(101);

        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment101);

        when(paymentServiceMock.getPayment(1)).thenReturn(payment101);

        when(paymentServiceMock.getCustomerPayments(customer)).thenReturn(paymentList);

        when(paymentServiceMock.create(paymentDto101)).thenReturn(payment101);
        when(paymentServiceMock.create(paymentDto102)).thenReturn(null);

        when(customerServiceMock.getCustomer(101)).thenReturn(customer);
        when(customerServiceMock.getCustomer(102)).thenReturn(null);

    }


//    @Before
//    public void setup() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .alwaysDo(print())
//                .build();
//
//        userDetails = userDetailsService.loadUserByUsername("user3");
//
//        User user = mock(User.class);
//
//        user.setUserEmail("test@test.com");
//        user.setUserName("Mocked one");
//
//        when(userRepository.getOne(1)).thenReturn(user);
//    }

//    @Test
//    @WithMockUser(roles = "USER")
//    public void testUserCanReachRoot() throws Exception {
//
//        List<User> mockedList = mock(List.class);
//
//        User user = userRepository.getOne(1);
//
//        mockedList.add(user);
//
//        mockMvc.perform(get("/").param("users", String.valueOf(mockedList)))
//                .andExpect(status().isOk());
//    }


    @Test
    @WithMockUser(roles = "USER")
    public void httpBasicAuthenticationSuccess() throws Exception {
        mockMvc.perform(get("/notfoundpage"))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "USER")
    public void getPaymentById() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
       MvcResult mvcResult = mockMvc
                .perform(get("/api/payments/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
        ;
//       System.out.println("Res: " + mvcResult.getResponse().getContentAsString());
//       PaymentDTO paymentDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PaymentDTO.class);
//       System.out.println("PaymentDTO: " + paymentDTO.toString());
    }


    @Test
    @WithMockUser(roles = "USER")
    public void getPaymentByCustomer_thenOk() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/paymentsall/101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                ;
    }


    @Test
    @WithMockUser(roles = "USER")
    public void getPaymentByCustomer_thenNotFound() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/api/paymentsall/102"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn()
                ;
    }


    @Test
    @WithMockUser(roles = "USER")
    public void createPayment_thenCreated() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = mockMvc
                .perform(post("/api/paymentcreate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentDto101)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                ;
    }


    @Test
    @WithMockUser(roles = "USER")
    public void createPayment_thenBedRequest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = mockMvc
                .perform(post("/api/paymentcreate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentDto102)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                ;
    }



//
//
//    @Test
//    public void authenticationFailed() throws Exception {
//        mockMvc.perform(formLogin("/clogin").user("user3").password("invalid"))
//                .andExpect(status().isFound())
//                .andExpect(redirectedUrl("/clogin?error"))
//                .andExpect(unauthenticated());
//    }
//
//    @Test
//    public void doLogin() {
//        MockHttpServletRequestBuilder securedResourceAccess = get("/protected");
//        try {
//            MvcResult unauthenticatedResult = mockMvc
//                    .perform(securedResourceAccess)
//                    .andExpect(status().is3xxRedirection())
//                    .andReturn();
//
//
//            String loginUrl = unauthenticatedResult
//                    .getResponse()
//                    .getRedirectedUrl();
//            MvcResult authenticatedResult = mockMvc
//                    .perform(
//                            formLogin(loginUrl)
//                                    .user("user3")
//                                    .password("pass1"))
//                    .andExpect(status().is3xxRedirection())
//                    .andExpect(authenticated().withUsername("user3"))
//                    //can add to authenticated() .withRoles("USER","ADMIN","SUPERADMIN") - all roles which can access to URL
//                    .andReturn();
//
//            MockHttpSession session = (MockHttpSession) authenticatedResult
//                    .getRequest()
//                    .getSession();
//
//            mockMvc
//                    .perform(securedResourceAccess.session(session))
//                    .andExpect(status().isOk());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("Login failed");
//        }
//
//    }


}
