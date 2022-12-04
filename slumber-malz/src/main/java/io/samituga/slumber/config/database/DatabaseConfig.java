package io.samituga.slumber.config.database;

import static io.samituga.slumber.validator.Validator.notBlank;

/**
 * Configuration class with information to connect and configure a database
 */
public class DatabaseConfig {
    private final String driverClass;
    private final String url;
    private final String user;
    private final String password;

    DatabaseConfig(String driverClass, String url, String user, String password) {
        this.driverClass = notBlank("driverClass", driverClass);
        this.url = notBlank("url", url);
        this.user = notBlank("user", user);
        this.password = notBlank("password", password);
    }


    public static DatabaseConfigBuilder builder() {
        return new DatabaseConfigBuilder();
    }


    public String getDriverClass() {
        return driverClass;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
