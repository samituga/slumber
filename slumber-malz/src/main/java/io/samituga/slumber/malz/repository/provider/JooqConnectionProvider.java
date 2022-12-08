package io.samituga.slumber.malz.repository.provider;

import java.sql.Connection;
import java.sql.SQLException;
import org.jooq.ConnectionProvider;
import org.jooq.exception.DataAccessException;

public class JooqConnectionProvider implements ConnectionProvider {

    private final DataSourceProvider dataSourceProvider;

    public JooqConnectionProvider(DataSourceProvider dataSourceProvider) {
        this.dataSourceProvider = dataSourceProvider;
    }

    @Override
    public Connection acquire() throws DataAccessException {
        try {
            return dataSourceProvider.connection();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public void release(Connection connection) throws DataAccessException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
