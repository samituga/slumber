package io.samituga.slumber.config.database;

import static io.samituga.slumber.validator.Validator.notBlank;

/**
 * Configuration class with information to connect and configure a database
 */
public class DatabaseConfig {
    private final String driverClass;
    private final String jdbcUrl;
    private final String user;
    private final String password;

    DatabaseConfig(String driverClass, String jdbcUrl, String user, String password) {
        this.driverClass = notBlank("driverClass", driverClass);
        this.jdbcUrl = notBlank("jdbcUrl", jdbcUrl);
        this.user = notBlank("user", user);
        this.password = notBlank("password", password);
    }


    public static DatabaseConfigBuilder builder() {
        return new DatabaseConfigBuilder();
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
