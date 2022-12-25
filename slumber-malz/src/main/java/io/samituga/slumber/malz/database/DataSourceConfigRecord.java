package io.samituga.slumber.malz.database;

import static io.samituga.slumber.heimer.validator.Validator.notBlank;

/**
 * Configuration class with information to connect and configure a database
 */
record DataSourceConfigRecord(String driverClass, String jdbcUrl, String user,
                              String password) implements DataSourceConfig {

    DataSourceConfigRecord(String driverClass, String jdbcUrl, String user, String password) {
        this.driverClass = notBlank("driverClass", driverClass);
        this.jdbcUrl = notBlank("jdbcUrl", jdbcUrl);
        this.user = notBlank("user", user);
        this.password = notBlank("password", password);
    }

    @Override
    public String getDriverClass() {
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
