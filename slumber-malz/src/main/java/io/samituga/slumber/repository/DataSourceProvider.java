package io.samituga.slumber.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataSourceProvider {

    Connection connection() throws SQLException;
}
