package io.samituga.slumber.config.database;

public class DatabaseConfig {
    private final String driverClass;
    private final String url;
    private final String user;
    private final String password;

    DatabaseConfig(String driverClass, String url, String user, String password) {
        this.driverClass = driverClass;
        this.url = url;
        this.user = user;
        this.password = password;
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
