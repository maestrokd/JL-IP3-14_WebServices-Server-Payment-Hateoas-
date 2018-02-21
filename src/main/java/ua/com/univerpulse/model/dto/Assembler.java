package ua.com.univerpulse.model.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import ua.com.univerpulse.controllers.AppController;
import ua.com.univerpulse.model.entities.Payment;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Danny Briskin (sql.coach.kiev@gmail.com)
 */
public class Assembler {

    private static volatile Assembler instance;

    public static Assembler getInstance() {
        if (instance == null) {
            synchronized (Assembler.class) {
                if (instance == null) {
                    instance = new Assembler();
                }
            }
        }
        return instance;
    }

    public List<PaymentDTO> getPaymentDToList(List<Payment> paymentList) {
        List<PaymentDTO> paymentDTOList = new ArrayList<>();
        for (Payment payment : paymentList) {
            paymentDTOList.add(new PaymentDTO(payment));
        }
        return paymentDTOList;
    }

    public PaymentDTO getPaymentDTO(Payment payment) {
        Link selfLink = linkTo(methodOn(AppController.class)
                .getPayment(payment.getId()))
//                .slash(payment.getId())
                .withSelfRel();

        PaymentDTO paymentDTO = new PaymentDTO(payment);
        paymentDTO.add(selfLink);

        return paymentDTO;
    }

    public Page<PaymentDTO> getPaymentDToPage(Page<Payment> page, Pageable pageable) {
        List<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (Payment p : page) {
            paymentDTOS.add(getPaymentDTO(p));
        }
        return new PageImpl<>(paymentDTOS, pageable, page.getTotalPages());
    }
}
