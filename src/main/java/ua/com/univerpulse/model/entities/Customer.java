package ua.com.univerpulse.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "customer")

public class Customer {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;


    @Column(name = "phone_number", length = 25)
    private String phoneNumber = null;


    @Column(name = "billing_address")
    private String billingAddress;


    @ManyToOne
    @JoinColumn(name = "customer_status_id", foreignKey = @ForeignKey(name = "fk_customer_status_customer"))
    private CustomerStatus customerStatus;


    @Column(name = "activated_date")
    private Date activatedDate;


    @Column(name = "deactivated_date")
    private Date deactivatedDate;


    @Column(name = "balance")
    private float balance;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_customer"))
    private User user;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CustomerService> customerServiceList = new ArrayList<>();

    public  CustomerService addService(Service service, ServiceStatus serviceStatus) {
        System.out.println("Add Service");
        CustomerService customerService = new CustomerService();
        customerService.setCustomer(this);
        customerService.setService(service);
        customerService.setServiceStatus(serviceStatus);
        customerServiceList.add(customerService);
        service.getCustomerServiceList().add(customerService);
        return customerService;
    }

    public void removeService(Service service) {
        CustomerService customerServiceTemp = null;
        for (CustomerService customerService : customerServiceList) {
            if (service.getName().equals(customerService.getService().getName())) {
                customerServiceTemp = customerService;
                break;
            }
        }
        if (customerServiceTemp == null) {
            return;
        } else {
            customerServiceList.remove(customerServiceTemp);
            service.getCustomerServiceList().remove(customerServiceTemp);
        }
    }

//    @ManyToMany
//    @JoinTable(name = "cus_ser",
//            joinColumns = {@JoinColumn(name = "customer_id")},
//            inverseJoinColumns = {@JoinColumn(name = "servise_id")})
//    private List<Service> serviceList = new ArrayList<>();


    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true, mappedBy = "customer")
    private Set<Event> eventList = new HashSet<>();

    public void addEvent(Service service) {
        Event event = new Event();
        event.setCustomer(this);
        event.setService(service);
        event.setDate(new Date());
        event.setCost(service.getPayroll());
        eventList.add(event);
        service.getEventList().add(event);
    }


    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true, mappedBy = "customer")
    private List<Bill> billList = new ArrayList<>();


//    public  void addBill(String startDate, String endDate, Dao dao) {
////        Dao dao = new  Dao();
//        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-mm-dd HH:mm:ss");
//        Date parsingStartDate = null;
//        Date parsingEndDate = null;
//        try {
//            parsingStartDate = ft.parse(startDate);
//            parsingEndDate = ft.parse(endDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Bill bill = new Bill();
//        bill.setCustomer(this);
//        bill.setStartDate(parsingStartDate);
//        bill.setEndDate(parsingEndDate);
//        bill.setAmount(dao.getAmountForPeriodForCustomer(this, startDate, endDate));
//        billList.add(bill);
////        dao.endDao();
//    }

    // to avoid infinite recursion in JSON master-detail-master...
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true, mappedBy = "customer")
    private List<Payment> paymentList = new ArrayList<>();


    // Constructors
    public Customer() {}


    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatus customerStatus) {
        if (this.customerStatus.getId() == 1 && customerStatus.getId() == 2) {
            this.activatedDate = new Date();
        }
        if (this.customerStatus.getId() == 2 && customerStatus.getId() == 1) {
            this.deactivatedDate = new Date();
        }
        this.customerStatus = customerStatus;
    }

    public Date getActivatedDate() {
        return activatedDate;
    }

    public void setActivatedDate(Date activatedDate) {
        this.activatedDate = activatedDate;
    }

    public Date getDeactivatedDate() {
        return deactivatedDate;
    }

    public void setDeactivatedDate(Date deactivatedDate) {
        this.deactivatedDate = deactivatedDate;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CustomerService> getCustomerServiceList() {
        return customerServiceList;
    }

    public void setCustomerServiceList(List<CustomerService> customerServiceList) {
        this.customerServiceList = customerServiceList;
    }

    public Set<Event> getEventList() {
        return eventList;
    }

    public void setEventList(Set<Event> eventList) {
        this.eventList = eventList;
    }

    public List<Bill> getBillList() {
        return billList;
    }

    public void setBillList(List<Bill> billList) {
        this.billList = billList;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }


    // Methods

    @Override
    public String toString() {
        return "Customer{" +
                "phoneNumber='" + phoneNumber + '\'' +
//                ", customerStatus='" + customerStatus.getName() + '\'' +
                ", balance=" + balance +
//                ", user=" + user.getLogin() +
                ", hash=" + this.hashCode() +
//                ", customerServiceList=" + customerServiceList +
               // ", eventList=" + eventList +
//                ", billList=" + billList +
//                ", paymentList=" + paymentList +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
