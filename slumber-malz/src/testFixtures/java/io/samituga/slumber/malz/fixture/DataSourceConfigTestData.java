package io.samituga.slumber.malz.fixture;

import static io.samituga.slumber.malz.database.DataSourceConfigBuilder.dataSourceConfigBuilder;

import io.samituga.slumber.malz.database.DataSourceConfig;
import io.samituga.slumber.malz.driver.Driver;

public class DataSourceConfigTestData {

    public static DataSourceConfig aDataSourceConfig() {
        return dataSourceConfigBuilder()
              .driverClass(Driver.POSTGRES)
              .jdbcUrl("jdbc:postgresql://host:port/database?properties")
              .user("user")
              .password("password")
              .build();
    }
}
