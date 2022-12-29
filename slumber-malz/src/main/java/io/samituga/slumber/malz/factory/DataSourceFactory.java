package io.samituga.slumber.malz.factory;

import io.samituga.slumber.malz.database.DataSourceConfig;
import javax.sql.DataSource;

/**
 * Factory that creates {@link DataSource} instances.
 */
public interface DataSourceFactory {

    /**
     * Instantiates a {@link DataSource} implementation.
     *
     * @param dataSourceConfig the datasource configuration
     * @return the {@link DataSource} instance
     */
    DataSource dataSource(DataSourceConfig dataSourceConfig);
}
