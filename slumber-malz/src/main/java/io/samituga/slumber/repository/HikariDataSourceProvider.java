package io.samituga.slumber.repository;

import io.samituga.slumber.config.database.DataSourceConfig;
import io.samituga.slumber.factory.DataSourceFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class HikariDataSourceProvider implements DataSourceProvider {

    private final DataSourceFactory dataSourceFactory;
    private final DataSourceConfig dataSourceConfig;

    public HikariDataSourceProvider(DataSourceFactory dataSourceFactory, DataSourceConfig dataSourceConfig) {
        this.dataSourceFactory = dataSourceFactory;
        this.dataSourceConfig = dataSourceConfig;
    }

    @Override
    public Connection connection() throws SQLException {
        return dataSourceFactory.dataSource(dataSourceConfig).getConnection();
    }
}
