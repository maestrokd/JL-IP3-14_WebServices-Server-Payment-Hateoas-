package ua.com.univerpulse.model.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer_status")
public class CustomerStatus {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //    @Id
    @Column(name = "name")
    private String name = "deactive";

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true, mappedBy = "customerStatus", fetch = FetchType.EAGER)
    private Set<Customer> customerList = new HashSet<>();

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

    public Set<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(Set<Customer> customerList) {
        this.customerList = customerList;
    }


    // Methods

    @Override
    public String toString() {
        return "CustomerStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customerList=" + customerList +
                '}';
    }
}
