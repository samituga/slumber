package io.samituga.slumber.config.database;

import io.samituga.slumber.builder.Builder;

public class DatabaseConfigBuilder implements Builder<DatabaseConfig> {

    private String driverClass;
    private String url;
    private String user;
    private String password;


    public DatabaseConfigBuilder() {

    }

    public DatabaseConfigBuilder driverClass(String driverClass) {
        this.driverClass = driverClass;
        return this;
    }

    public DatabaseConfigBuilder url(String url) {
        this.url = url;
        return this;
    }

    public DatabaseConfigBuilder user(String user) {
        this.user = user;
        return this;
    }

    public DatabaseConfigBuilder password(String password) {
        this.password = password;
        return this;
    }

    @Override
    public DatabaseConfig build() {
        return new DatabaseConfig(driverClass, url, user, password);
    }
}
