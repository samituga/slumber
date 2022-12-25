package io.samituga.slumber.malz.repository;

import static io.samituga.slumber.heimer.validator.Validator.required;

import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Repository {
    static {
        System.setProperty("org.jooq.no-logo", "true");
    }
    private static final Logger LOG = LoggerFactory.getLogger(Repository.class);

    protected final DSLContext dslContext;

    public Repository(ConnectionProvider connectionProvider) {
        required("connectionProvider", connectionProvider);
        Settings settings = new Settings();
        settings.setStatementType(StatementType.STATIC_STATEMENT);
        settings.setExecuteLogging(false);
        settings.setRenderSchema(false);
        settings.setAttachRecords(false);

        this.dslContext = DSL.using(configuration(connectionProvider));
//        this.dslContext = DSL.using(connectionProvider, SQLDialect.POSTGRES, settings);
    }

    private Configuration configuration(ConnectionProvider connectionProvider) {
        return new DefaultConfiguration()
              .set(connectionProvider)
              .set(SQLDialect.POSTGRES)
              .set(new DefaultExecuteListenerProvider(new PrettyPrinter()));
    }

    public static class PrettyPrinter implements ExecuteListener {

        /**
         * Hook into the query execution lifecycle before executing queries
         */
        @Override
        public void executeStart(ExecuteContext ctx) {

            if (ctx.batchSQL().length > 0) {
                for (String s : ctx.batchSQL()) {
                    LOG.info(s);
                }
            } else {
                LOG.info(ctx.sql());
            }
        }
    }
}
