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

/**
 * @author Danny Briskin (sql.coach.kiev@gmail.com)
 * <p>
 * Payments controller
 * 1. Get payments:
 * 1.1 Get payment by id
 * 1.2 Get payment by id and customerID
 * 1.3 Get payments list by customerID
 * 2. Create payments:
 * 2.1 Create payment of customerID
 * 3. Update payments:
 * 3.1 Update payment by id
 * 4. Delete payments:
 * 4.1 Delete payment by id
 */
@RestController
public class AppController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CustomerService customerService;


    @RequestMapping(value = "/api/paymentz"
            , method = RequestMethod.GET
            , produces = {"application/json"}
//            , headers = {"Access-Control-Allow-Origin:*"}
            )
    public PageResource<PaymentDTO> paymentz(Pageable pageable
//            ,@RequestParam int page
//            ,@RequestParam int size
    ) {
        System.out.println("COME");
//        List <PaymentDTO> paymentDTOList = Assembler.getInstance().getPaymentDToList()
        Page<Payment> pageN = paymentService.findAll(pageable);
        Page<PaymentDTO> paymentDTOS = Assembler.getInstance().getPaymentDToPage(pageN, pageable);
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
//            return new ResponseEntity<>(payment, HttpStatus.OK);
            return new ResponseEntity<>(Assembler.getInstance().getPaymentDTO(payment), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * 1.2 Get payment by id and customerID
     *
     * @param id         payment ID
     * @param customerId customerID
     * @return Payment
     */
    @RequestMapping(
            value = "/api/payments/{customerId}/{id}"
            , method = RequestMethod.GET
            , headers = {"Accept=application/json"}
    )
    public ResponseEntity<Payment> getPaymentByIdCustomerId
    (@PathVariable("customerId") int customerId, @PathVariable("id") int id) {
        Customer customer = customerService.getCustomer(customerId);
        if (customer != null) {
            Payment payment = paymentService.getPayment(id, customerId);
            if (payment != null) {
                return new ResponseEntity<>(payment, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * 1.3 Get payments list by customerID
     *
     * @param customerId id of customer
     * @return List of payments
     */
    @RequestMapping(
            value = "/api/paymentsall/{customerId}"
            , method = RequestMethod.GET
            , headers = {"Accept=application/json"})
    public ResponseEntity<List<Payment>> getPaymentsList(@PathVariable("customerId") int customerId) {
        Customer customer = customerService.getCustomer(customerId);
        if (customer != null) {
            List<Payment> paymentList = paymentService.getCustomerPayments(customer);
            return new ResponseEntity<>(paymentList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 2.1 Create payment of customerID
     *
     * @param paymentDTO json like {"customerId":"1","paymentDate":"11-11-2011","channel":"fignya","paymentAmount":"21"}
     * @return payment id, -1 if wrong
     */
    @PostMapping
            (value = "/api/paymentcreate", headers = {"Accept=application/json; charset=utf-8"})
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Integer createPaymentDto(@RequestBody PaymentDTO paymentDTO) {
        System.out.println("Come IN");
        return paymentService.create(paymentDTO);
    }


    /**
     * 3.1 Update payment by id
     *
     * @param id         payment ID
     * @param newPayment data to update. json like {"customerId":"1","paymentDate":"11-11-2011","channel":"fignya","paymentAmount":"21"}
     * @return "Payment " + id + " updated"
     */
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

    /**
     * 4.1 Delete payment by id
     *
     * @param id id of payment
     * @return "Payment " + id + " deleted"
     */
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
