package io.samituga.slumber.malz.database;

import io.samituga.slumber.ivern.structure.Structure;
import io.samituga.slumber.malz.driver.Driver;

public interface DataSourceConfig extends Structure<DataSourceConfig, DataSourceConfigBuilder> {

    Driver driverClass();

    String jdbcUrl();

    String user();

    String password(); // TODO: 2023-03-25 type
}
