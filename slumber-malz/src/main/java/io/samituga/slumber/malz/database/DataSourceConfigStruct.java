package io.samituga.slumber.malz.database;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;
import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredNotBlank;
import static io.samituga.slumber.malz.database.DataSourceConfigBuilder.dataSourceConfigBuilder;

import io.samituga.slumber.malz.driver.Driver;

record DataSourceConfigStruct(Driver driverClass,
                              String jdbcUrl,
                              String user,
                              String password)
      implements DataSourceConfig {

    DataSourceConfigStruct(Driver driverClass, String jdbcUrl, String user, String password) {
        this.driverClass = required("driverClass", driverClass);
        this.jdbcUrl = requiredNotBlank("jdbcUrl", jdbcUrl);
        this.user = requiredNotBlank("user", user);
        this.password = requiredNotBlank("password", password);
    }

    @Override
    public DataSourceConfigBuilder copy() {
        return dataSourceConfigBuilder()
              .driverClass(driverClass)
              .jdbcUrl(jdbcUrl)
              .user(user)
              .password(password);
    }
}
