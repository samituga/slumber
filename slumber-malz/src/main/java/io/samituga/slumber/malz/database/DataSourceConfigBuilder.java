package io.samituga.slumber.malz.database;

import io.samituga.slumber.heimer.builder.Builder;
public final class DataSourceConfigBuilder implements Builder<DataSourceConfig> {

    private String driverClass;
    private String jdbcUrl;
    private String user;
    private String password;


    DataSourceConfigBuilder() {}

    public DataSourceConfigBuilder driverClass(String driverClass) {
        this.driverClass = driverClass;
        return this;
    }

    public DataSourceConfigBuilder jdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
        return this;
    }

    public DataSourceConfigBuilder user(String user) {
        this.user = user;
        return this;
    }

    public DataSourceConfigBuilder password(String password) {
        this.password = password;
        return this;
    }

    @Override
    public DataSourceConfig build() {
        return new DataSourceConfig(driverClass, jdbcUrl, user, password);
    }

    @Override
    public DataSourceConfigBuilder copy(DataSourceConfig dataSourceConfig) {
        return new DataSourceConfigBuilder()
              .driverClass(dataSourceConfig.getDriverClass())
              .jdbcUrl(dataSourceConfig.getJdbcUrl())
              .user(dataSourceConfig.getUser())
              .password(dataSourceConfig.getPassword());
    }
}
