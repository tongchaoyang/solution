package interview.ripple.solution.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repo;

    @Override
    public List<Customer> getCustomerByFirstNameAndLastName(String firstName, String lastName) {
        return repo.getCustomerByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Customer getCustomerBySsn(String ssn) {
        return repo.getCustomerBySsn(ssn);
    }

    @Override
    public Customer getCustomerByPhoneNo(String phoneNo) {
        return repo.getCustomerByPhoneNo(phoneNo);
    }

    @Override
    public Customer save(Customer c) {
        return repo.save(c);
    }

    @Override
    public void delete(Customer c) {
        repo.delete(c);
    }

    @Override
    public List<Customer> getAllCustomers() {
        Iterator<Customer> it = repo.findAll().iterator();
        List<Customer> r = new ArrayList<>();
        while (it.hasNext()) {
            r.add(it.next());
        }
        return r;
    }
}
