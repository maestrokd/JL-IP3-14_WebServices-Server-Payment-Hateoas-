package ua.com.univerpulse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.univerpulse.controllers.PaymentController;


import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private PaymentController paymentController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(paymentController).isNotNull();
    }


}
