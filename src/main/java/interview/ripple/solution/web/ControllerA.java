package interview.ripple.solution.web;

import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import interview.ripple.solution.model.Account;
import interview.ripple.solution.model.AccountService;
import interview.ripple.solution.model.Customer;
import interview.ripple.solution.model.CustomerService;
import interview.ripple.solution.model.TxnIdGenerator;

@RestController
@RequestMapping(path="/api")
public class ControllerA {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TxnIdGenerator txnIdGenerator;
    
    private final Map<Integer, Account> cache = new WeakHashMap<>();
    public static final String Greeting = "Hello, "; 
    
    private Logger logger = LoggerFactory.getLogger(ControllerA.class);
    
    @RequestMapping(value="/ping/{str}", method=RequestMethod.GET)
    public String ping(@PathVariable String str) {
        logger.info("ping with " + str);
        return Greeting + str;
    }
    
    @RequestMapping(value="/nextTxnId", method=RequestMethod.GET)
    public int getNextTxnId() {
        int txnId = txnIdGenerator.getNextId();
        logger.info("Generated next transaction id: " + txnId);
        return txnId;
    }

    /**
    @RequestMapping(value="/newCustomer", method=RequestMethod.POST)
    public void save(Customer c) {
        customerService.save(c);
    }*/
    
    @RequestMapping(value="/newCustomer", method=RequestMethod.POST,  
            produces = {"application/json", "application/xml"})
    public Customer save(@RequestParam(value="firstName", required = true) String firstName, 
            @RequestParam(value="lastName", required = true)
            String lastName, @RequestParam(value="phoneNo", required = true) String phoneNo, 
            @RequestParam(value="email", required = true) String email) {
        Customer c = new Customer();
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setEmail(email);
        c.setPhoneNo(phoneNo);
        //c.setSsn(ssn);
        c = customerService.save(c);
        logger.info("created new user " + c.toString());
        Account a = new Account();
        a.setCustomer(c);
        a.setBalance(0);
        a = accountService.save(a);
        logger.info("created new account for customer " + a.toString());
        return c;
    }
    
    @RequestMapping(value="/customer/{phoneNo}", method=RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    public Customer getCustomerByPhoneNo(@PathVariable String phoneNo) {
        logger.info("retrieving user with phone number " + phoneNo);
        return customerService.getCustomerByPhoneNo(phoneNo);
    }
    
    @RequestMapping(value="/debit", method=RequestMethod.POST)
    public long debit(@RequestParam String phoneNo, @RequestParam long amt, 
            @RequestParam int txnId) {
        Customer c = customerService.getCustomerByPhoneNo(phoneNo);
        Account a = accountService.getAccountByCustomerId(c.getId());
        a.setBalance(a.getBalance() - amt);
        cache.put(txnId, a);
        logger.info(c.getFirstName() + "'s account " + a.getId() + " " + 
        "to be debited by " + amt + " in transaction " + txnId);
        return a.getBalance();
    }
    
    @RequestMapping(value="/credit", method=RequestMethod.POST)
    public long credit(@RequestParam String phoneNo, @RequestParam long amt,
            @RequestParam int txnId) {
        Customer c = customerService.getCustomerByPhoneNo(phoneNo);
        Account a = accountService.getAccountByCustomerId(c.getId());
        a.setBalance(a.getBalance() + amt);
        logger.info(c.getFirstName() + "'s account " + a.getId() + " " + 
        "to be credited by " + amt + " in transaction " + txnId);
        cache.put(txnId, a);
        return a.getBalance();
    }
    
    @RequestMapping(value="/commit/{txnId}", method=RequestMethod.POST)
    public boolean commit(@PathVariable int txnId) {
        Account a = cache.get(txnId);
        if (a == null)
            return false;
        
        a = accountService.save(a);
        logger.info("Account " + a.getId() + " has been updated in transaction " +
        txnId + ". It's balance is now " + a.getBalance());
        cache.remove(txnId);
        return true;
    }

}
