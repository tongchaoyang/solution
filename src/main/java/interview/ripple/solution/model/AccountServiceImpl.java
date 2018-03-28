package interview.ripple.solution.model;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository repo;
    @Override
    public Account getAccountByCustomerId(long customerId) {
        return repo.getAccountByCustomerId(customerId);
    }

    @Override
    public Account getAccountById(long id) {
        return repo.getAccountById(id);
    }

    @Override
    public Account save(Account a) {
        return repo.save(a);
    }
}
