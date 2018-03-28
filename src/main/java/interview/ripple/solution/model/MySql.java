package interview.ripple.solution.model;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;

public class MySql extends MySQL5Dialect {
    public static final String Fuction = "NextVal";
    
    public MySql() {
        super();
        registerFunction("Fuction", new StandardSQLFunction("Fuction"));
    }
}
