package ua.com.univerpulse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.univerpulse.model.dto.Assembler;
import ua.com.univerpulse.model.dto.PageResource;
import ua.com.univerpulse.model.dto.PaymentDTO;
import ua.com.univerpulse.model.entities.Customer;
import ua.com.univerpulse.model.entities.Payment;
import ua.com.univerpulse.model.services.CustomerService;
import ua.com.univerpulse.model.services.PaymentService;

import java.util.List;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@RestController
public class PaymentController {

    private PaymentService paymentService;
    private CustomerService customerService;


    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/api/paymentz"
            , method = RequestMethod.GET
            , produces = {"application/json"}
//            , headers = {"Access-Control-Allow-Origin:*"}
            )
    public PageResource<PaymentDTO> paymentz(Pageable pageable
//            ,@RequestParam int page
//            ,@RequestParam int size
    ) {
//        System.out.println("COME");
//        List <PaymentDTO> paymentDTOList = Assembler.getInstance().getPaymentDToList()
        Page<Payment> pageN = paymentService.findAll(pageable);
        System.out.println(pageN.getTotalPages());
        System.out.println(pageN.getTotalElements());

        Page<PaymentDTO> paymentDTOS = Assembler.getInstance().getPaymentDToPage(pageN);
        System.out.println("==========================");
        System.out.println(paymentDTOS.getTotalPages());
        System.out.println(paymentDTOS.getTotalElements());
//        for (Payment payment : pageN) {
//            Link selfLink = linkTo(methodOn(AppController.class)
//                    .getPayment(paymentDTO.getPaymentId()))
//                    .slash(paymentDTO.getId())
//                    .withSelfRel();
//            paymentDTO.add(selfLink);
//        }
        return new PageResource<>(paymentDTOS, "page", "size");
    }


    /**
     * 1.1 Get payment by id
     *
     * @param id paymen ID
     * @return Payment
     */

    @RequestMapping(
            value = "/api/payments/{id}"
            , method = RequestMethod.GET
            , headers = {"Accept=application/json"})
    // HttpEntity Represents an HTTP request or response entity, consisting of headers and body.
    // ResponseEntity это Extension of HttpEntity that adds a HttpStatus status code
    public ResponseEntity<PaymentDTO> getPayment(@PathVariable("id") int id) {
        Payment payment = paymentService.getPayment(id);
        if (payment != null) {
//            Assembler assembler = Assembler.getInstance();
//            PaymentDTO paymentDTO = assembler.getPaymentDTO(payment);

//            return new ResponseEntity<>(payment, HttpStatus.OK);
            return new ResponseEntity<>(Assembler.getInstance().getPaymentDTO(payment), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(
            value = "/api/paymentsall/{customerId}"
            , method = RequestMethod.GET
            , headers = {"Accept=application/json"})
    public ResponseEntity<List<PaymentDTO>> getPaymentsList(@PathVariable("customerId") int customerId) {
        Customer customer = customerService.getCustomer(customerId);
        if (customer != null) {
            List<Payment> paymentList = paymentService.getCustomerPayments(customer);
            return new ResponseEntity<>(Assembler.getInstance().getPaymentDToList(paymentList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
            (value = "/api/paymentcreate", headers = {"Accept=application/json; charset=utf-8"})
    public ResponseEntity<PaymentDTO> createPaymentDto(@RequestBody PaymentDTO paymentDTO) {
        System.out.println("Come IN");
        Payment paymentNew = paymentService.create(paymentDTO);
        if (paymentNew != null) {
            customerService.sumCustomerBalanceWithPaymentAmount(paymentNew.getCustomer().getId(), paymentNew.getAmount());
            PaymentDTO paymentDToNew = Assembler.getInstance().getPaymentDTO(paymentNew);
            return new ResponseEntity<>(paymentDToNew, HttpStatus.CREATED);
        }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/api/paymentupdate/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateUser(@PathVariable("id") int id
            , @RequestBody PaymentDTO newPayment) {
        Payment payment = paymentService.getPayment(id);
        if (payment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        paymentService.updatePayment(payment, newPayment);
        return new ResponseEntity<>("Payment " + id + " updated", HttpStatus.OK);
    }


    @RequestMapping(value = "/api/paymentdelete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {

        Payment payment = paymentService.getPayment(id);
        if (payment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        paymentService.deletePayment(id);
        return new ResponseEntity<>("Payment " + id + " deleted", HttpStatus.OK);
    }

}
