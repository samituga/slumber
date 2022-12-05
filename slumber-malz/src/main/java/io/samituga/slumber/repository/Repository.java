package io.samituga.slumber.repository;

import io.samituga.slumber.repository.command.DatabaseCommandsImpl;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

public abstract class Repository {

    private final DSLContext dslContext;
    protected final DatabaseCommandsImpl databaseCommands;

    public Repository(ConnectionProvider connectionProvider) {
        this.dslContext = DSL.using(configuration(connectionProvider));
        databaseCommands = new DatabaseCommandsImpl(dslContext);
    }

//    public DatabaseCommandsImpl commands() {
//        return databaseCommands;
//    }

    private Configuration configuration(ConnectionProvider connectionProvider) {
        return new DefaultConfiguration()
              .set(connectionProvider)
              .set(SQLDialect.POSTGRES);
    }
}
