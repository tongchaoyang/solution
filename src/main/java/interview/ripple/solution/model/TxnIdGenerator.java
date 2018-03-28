package interview.ripple.solution.model;

import java.sql.CallableStatement;
import java.sql.Types;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("txnIdGenerator")
public class TxnIdGenerator {
    private static MySql mysql = new MySql();
    @PersistenceContext
    private EntityManager em;
    
    public TxnIdGenerator() {
        
    }
    
    public int getNextId() {
        Session session = em.unwrap(Session.class);
        int r = session.doReturningWork(connection -> {
            try (CallableStatement function = connection.prepareCall(
                    "{ ? = call NextVal(?) }")) {
                function.registerOutParameter(1, Types.INTEGER);
                function.setString(2, "transaction_id_seq");
                function.execute();
                return function.getInt(1);
            }
        });
        return r;
    }
}
