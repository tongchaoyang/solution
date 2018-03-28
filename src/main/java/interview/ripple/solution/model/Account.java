package interview.ripple.solution.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"customerId"}))
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId")
    private Customer customer;
    
    private long balance;
    private int version;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public long getBalance() {
        return balance;
    }
    public void setBalance(long balance) {
        this.balance = balance;
    }
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    
    @Override
    public String toString(){
        return id + ": " + customer.getFirstName() + " " + customer.getLastName() + ", " + balance;
    }
}
