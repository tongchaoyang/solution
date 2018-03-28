package interview.ripple.solution.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("customerRepository")
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer getCustomerBySsn(String ssn);
    Customer getCustomerByPhoneNo(String phoneNo);
    List<Customer> getCustomerByFirstNameAndLastName(String firstName, String lastName);
}
