package ua.com.univerpulse.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name = "payments")
@Component
public class Payment{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    // to avoid infinite recursion in JSON master-detail-master...
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_customers_payments"))
    private Customer customer;

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false)
    private Date date;

    @Column(name = "amount")
    private Float amount;

    @Column
    private String channel;

    public Payment() {
    }

    public Payment(Customer customer, Float amount, String channel) {
        this.customer = customer;
        this.amount = amount;
        this.channel = channel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDateAsString(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.date = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDateAsString() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return  simpleDateFormat.format(this.date);
    }


    public String getDateAndTimeAsString() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return  simpleDateFormat.format(this.date);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (customer != null ? !customer.equals(payment.customer) : payment.customer != null) return false;
        if (date != null ? !date.equals(payment.date) : payment.date != null) return false;
        if (amount != null ? !amount.equals(payment.amount) : payment.amount != null)
            return false;
        return channel != null ? channel.equals(payment.channel) : payment.channel == null;
    }

    @Override
    public int hashCode() {
        int result = customer != null ? customer.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", customer=" + customer.getId() +
                ", date=" + date +
                ", amount=" + amount +
                ", channel='" + channel + '\'' +
                '}';
    }
}
