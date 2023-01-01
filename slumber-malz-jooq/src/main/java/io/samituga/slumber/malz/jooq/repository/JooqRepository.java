package io.samituga.slumber.malz.jooq.repository;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

public abstract class JooqRepository {

    protected final DSLContext dslContext;

    public JooqRepository(ConnectionProvider connectionProvider) {
        required("connectionProvider", connectionProvider);
        this.dslContext = DSL.using(configuration(connectionProvider));
    }

    private Configuration configuration(ConnectionProvider connectionProvider) {
        return new DefaultConfiguration()
              .set(connectionProvider)
              .set(SQLDialect.POSTGRES);
    }
}
