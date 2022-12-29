package io.samituga.slumber.malz.factory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.samituga.slumber.malz.database.DataSourceConfig;
import javax.sql.DataSource;

public class HikariDataSourceFactory implements DataSourceFactory {

    @Override
    public DataSource dataSource(DataSourceConfig dataSourceConfig) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(dataSourceConfig.driverClass().className);
        config.setJdbcUrl(dataSourceConfig.jdbcUrl());
        config.setUsername(dataSourceConfig.user());
        config.setPassword(dataSourceConfig.password());

        return new HikariDataSource(config);
    }
}
