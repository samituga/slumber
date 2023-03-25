package io.samituga.slumber.malz.jooq.config;

import io.samituga.slumber.ivern.builder.Builder;
import io.samituga.slumber.malz.database.DataSourceConfig;
import io.samituga.slumber.malz.driver.Driver;

public final class DataSourceConfigBuilder implements Builder<DataSourceConfig> {

    private Driver driverClass;
    private String jdbcUrl;
    private String user;
    private String password;


    private DataSourceConfigBuilder() {}

    public static DataSourceConfigBuilder builder() {
        return new DataSourceConfigBuilder();
    }

    public DataSourceConfigBuilder driverClass(Driver driverClass) {
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
        return new DataSourceConfigImpl(driverClass, jdbcUrl, user, password);
    }
}
