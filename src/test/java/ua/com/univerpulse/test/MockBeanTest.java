package ua.com.univerpulse.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.univerpulse.model.dto.PaymentDTO;
//import ua.com.univerpulse.springtestingmocks.model.entity.User;
//import ua.com.univerpulse.springtestingmocks.model.repository.UserRepository;

import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Danny Briskin (sql.coach.kiev@gmail.com)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockBeanTest {

//    @MockBean
//    UserRepository userRepository;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    private UserDetails userDetails;

    @Autowired
    private MockMvc mockMvc;




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
       System.out.println("Res: " + mvcResult.getResponse().getContentAsString());
       PaymentDTO paymentDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PaymentDTO.class);
       System.out.println("PaymentDTO: " + paymentDTO.toString());
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
