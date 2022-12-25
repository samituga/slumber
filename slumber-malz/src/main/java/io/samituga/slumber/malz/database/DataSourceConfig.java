package io.samituga.slumber.malz.database;

public sealed interface DataSourceConfig permits DataSourceConfigRecord {

    static DataSourceConfigBuilder builder() {
        return new DataSourceConfigBuilder();
    }

    /**
     * Gets the database driver class eg: org.postgresql.Driver.
     *
     * @return the database driver class
     */
    String getDriverClass();

    /**
     * Gets the database url.
     *
     * @return the database url
     */
    String getJdbcUrl();

    /**
     * Gets the database username.
     *
     * @return the database username
     */
    String getUser();

    /**
     * Gets the database password.
     *
     * @return the database password
     */
    String getPassword();
}
