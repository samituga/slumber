package io.samituga.slumber.malz.repository;

import static io.samituga.slumber.heimer.validator.Validator.required;

import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

public abstract class Repository {

    protected final DSLContext dslContext;

    public Repository(ConnectionProvider connectionProvider) {
        required("connectionProvider", connectionProvider);
        this.dslContext = DSL.using(configuration(connectionProvider));
    }

    private Configuration configuration(ConnectionProvider connectionProvider) {
        return new DefaultConfiguration()
              .set(connectionProvider)
              .set(SQLDialect.POSTGRES);
    }
}
