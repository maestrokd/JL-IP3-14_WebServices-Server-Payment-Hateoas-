package ua.com.univerpulse.model.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "customer_service")
public class CustomerServiceEntity implements Serializable {


    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

//    @Id
    @ManyToOne
//    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_customer_customer_services"))
    private Customer customer;

//    @Id
    @ManyToOne
//    @JoinColumn(name = "service_id", foreignKey = @ForeignKey(name = "fk_service_customer_services"))
    private Service service;

    @ManyToOne
    @JoinColumn(name = "service_status_id", foreignKey = @ForeignKey(name = "fk_service_status_customer_services"))
    private ServiceStatus serviceStatus;

    // Constructors
    public CustomerServiceEntity() {
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}
