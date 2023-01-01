package io.samituga.slumber.malz.jooq.config;

import io.samituga.slumber.malz.database.DataSourceConfig;
import io.samituga.slumber.malz.driver.Driver;

import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredNotBlank;
import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

/**
 * Configuration class with information to connect and configure a database
 */
record DataSourceConfigImpl(Driver driverClass, String jdbcUrl, String user,
                            String password) implements DataSourceConfig {

    DataSourceConfigImpl(Driver driverClass, String jdbcUrl, String user, String password) {
        this.driverClass = required("driverClass", driverClass);
        this.jdbcUrl = requiredNotBlank("jdbcUrl", jdbcUrl);
        this.user = requiredNotBlank("user", user);
        this.password = requiredNotBlank("password", password);
    }
}
