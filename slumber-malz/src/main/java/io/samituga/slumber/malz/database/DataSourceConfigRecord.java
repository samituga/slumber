package io.samituga.slumber.malz.database;

import static io.samituga.slumber.heimer.validator.Validator.notBlank;
import static io.samituga.slumber.heimer.validator.Validator.required;

import io.samituga.slumber.malz.driver.Driver;

/**
 * Configuration class with information to connect and configure a database
 */
record DataSourceConfigRecord(Driver driverClass, String jdbcUrl, String user,
                              String password) implements DataSourceConfig {

    DataSourceConfigRecord(Driver driverClass, String jdbcUrl, String user, String password) {
        this.driverClass = required("driverClass", driverClass);
        this.jdbcUrl = notBlank("jdbcUrl", jdbcUrl);
        this.user = notBlank("user", user);
        this.password = notBlank("password", password);
    }

    @Override
    public Driver getDriverClass() {
        return driverClass;
    }

    @Override
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    @Override
    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
