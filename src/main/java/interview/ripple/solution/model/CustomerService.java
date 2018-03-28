package interview.ripple.solution.model;

import java.util.List;

public interface CustomerService {
    Customer getCustomerBySsn(String ssn);
    Customer getCustomerByPhoneNo(String phoneNo);
    List<Customer> getCustomerByFirstNameAndLastName(String firstName, String lastName);
    List<Customer> getAllCustomers();
    Customer save(Customer c);
    void delete(Customer c);
}
