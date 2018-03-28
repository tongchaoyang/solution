package interview.ripple.solution.model;

public interface AccountService {
    Account getAccountByCustomerId(long customerId);
    Account getAccountById(long id);
    Account save(Account a);
}
