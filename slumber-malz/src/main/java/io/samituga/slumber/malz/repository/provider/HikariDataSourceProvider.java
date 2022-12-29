package io.samituga.slumber.malz.repository.provider;

import io.samituga.slumber.malz.database.DataSourceConfig;
import io.samituga.slumber.malz.factory.DataSourceFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class HikariDataSourceProvider implements DataSourceProvider {

    private final DataSourceFactory dataSourceFactory;
    private final DataSourceConfig dataSourceConfig;

    public HikariDataSourceProvider(DataSourceFactory dataSourceFactory,
                                    DataSourceConfig dataSourceConfig) {
        this.dataSourceFactory = dataSourceFactory;
        this.dataSourceConfig = dataSourceConfig;
    }

    @Override
    public Connection connection() throws SQLException {
        return dataSourceFactory.dataSource(dataSourceConfig).getConnection();
    }
}
