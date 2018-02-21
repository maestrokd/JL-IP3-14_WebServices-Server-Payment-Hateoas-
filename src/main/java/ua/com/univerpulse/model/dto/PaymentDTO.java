package ua.com.univerpulse.model.dto;

import org.springframework.hateoas.ResourceSupport;
import ua.com.univerpulse.model.entities.Payment;

public class PaymentDTO extends ResourceSupport {

    private Integer paymentId;

    private Integer customerId;

    private Float amount;

    private String date;

    private String channel;

    public PaymentDTO() {
    }

    public PaymentDTO(Integer paymentId, Integer customerId, Float amount, String date, String channel) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.amount = amount;
        this.date = date;
        this.channel = channel;
    }

    public PaymentDTO(Payment payment) {
        this.paymentId = payment.getId();
        this.customerId = payment.getCustomer().getId();
        this.amount = payment.getAmount();
        this.date = payment.getDateAsString();
        this.channel = payment.getChannel();
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public Integer getCustomerId() {
        return customerId;
    }


    public Float getAmount() {
        return amount;
    }


    public String getDate() {
        return date;
    }


    public String getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "customerId=" + customerId +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}
