package ua.com.univerpulse.model.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service_status")
public class ServiceStatus {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

//    @Id
    @Column(name = "name")
    private String name = "deactive";

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true, mappedBy = "serviceStatus")
    private Set<CustomerServiceEntity> customerServiceEntityList = new HashSet<>();


    // Getter and Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CustomerServiceEntity> getCustomerServiceEntityList() {
        return customerServiceEntityList;
    }

    public void setCustomerServiceEntityList(Set<CustomerServiceEntity> customerServiceEntityList) {
        this.customerServiceEntityList = customerServiceEntityList;
    }
}
