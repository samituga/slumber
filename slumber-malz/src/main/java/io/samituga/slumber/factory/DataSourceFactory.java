package io.samituga.slumber.factory;

import io.samituga.slumber.config.database.DataSourceConfig;
import javax.sql.DataSource;

public interface DataSourceFactory {
    DataSource dataSource(DataSourceConfig dataSourceConfig);
}
