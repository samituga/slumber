package io.samituga.slumber.malz.database;

import io.samituga.slumber.malz.driver.Driver;

import static io.samituga.slumber.heimer.validator.AssertionUtility.notBlank;
import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

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
}
