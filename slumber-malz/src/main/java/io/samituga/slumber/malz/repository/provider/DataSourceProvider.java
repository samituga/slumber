package io.samituga.slumber.malz.repository.provider;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataSourceProvider {

    Connection connection() throws SQLException;
}
