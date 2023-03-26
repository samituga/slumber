package io.samituga.slumber.malz.fixture;

import static io.samituga.slumber.malz.database.DataSourceConfigBuilder.dataSourceConfigBuilder;

import io.samituga.slumber.malz.database.DataSourceConfigBuilder;
import io.samituga.slumber.malz.driver.Driver;

public class DataSourceConfigTestData {

    public static DataSourceConfigBuilder aDataSourceConfig() {
        return dataSourceConfigBuilder()
              .driverClass(Driver.POSTGRES)
              .jdbcUrl("jdbc:postgresql://host:port/database?properties")
              .user("user")
              .password("password");
    }
}
