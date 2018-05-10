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
//        this.date = payment.getDateAsString();
        this.date = payment.getDateAndTimeAsString();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PaymentDTO that = (PaymentDTO) o;

        if (paymentId != null ? !paymentId.equals(that.paymentId) : that.paymentId != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return channel != null ? channel.equals(that.channel) : that.channel == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (paymentId != null ? paymentId.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        return result;
    }

}
