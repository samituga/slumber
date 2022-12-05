package io.samituga.slumber.repository;

import java.sql.Connection;
import java.sql.SQLException;
import org.jooq.ConnectionProvider;
import org.jooq.exception.DataAccessException;

public class JooqConnectionProvider implements ConnectionProvider {

    private final HikariDataSourceProvider hikariDataSourceProvider;

    public JooqConnectionProvider(HikariDataSourceProvider hikariDataSourceProvider) {
        this.hikariDataSourceProvider = hikariDataSourceProvider;
    }

    @Override
    public Connection acquire() throws DataAccessException {
        try {
            return hikariDataSourceProvider.connection();
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
