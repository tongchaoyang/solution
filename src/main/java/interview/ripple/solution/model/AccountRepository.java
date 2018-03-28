package interview.ripple.solution.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("accountRepository")
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account getAccountByCustomerId(long customerId);
    Account getAccountById(long id);
}
