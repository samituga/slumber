package io.samituga.slumber.config.database;

import static io.samituga.slumber.malz.driver.Driver.POSTGRES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.samituga.slumber.heimer.exception.ValidationException;
import io.samituga.slumber.malz.database.DataSourceConfig;
import io.samituga.slumber.malz.driver.Driver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class DataSourceConfigTest {


    @Test
    void should_create_instance_when_arguments_are_valid() {
        var driverClass = POSTGRES;
        var jdbcUrl = "jdbc:postgresql://host:port/database?properties";
        var user = "user";
        var password = "password";

        var dbConfig = DataSourceConfig.builder()
              .driverClass(driverClass)
              .jdbcUrl(jdbcUrl)
              .user(user)
              .password(password)
              .build();

        assertEquals(driverClass, dbConfig.getDriverClass());
        assertEquals(jdbcUrl, dbConfig.getJdbcUrl());
        assertEquals(user, dbConfig.getUser());
        assertEquals(password, dbConfig.getPassword());
    }

    @ParameterizedTest
    @MethodSource("io.samituga.slumber.config.database.DataSourceConfigDataProvider#constructor_parameters_with_invalid_arguments")
    void should_fail_validation_when_mandatory_fields_are_invalid(Driver driverClass,
                                                                  String jdbcUrl,
                                                                  String user,
                                                                  String password) {
        assertThrows(ValidationException.class,
              () -> DataSourceConfig.builder()
                    .driverClass(driverClass)
                    .jdbcUrl(jdbcUrl)
                    .user(user)
                    .password(password)
                    .build());
    }

    @Test
    void should_create_exact_copy() {
        var driverClass = POSTGRES;
        var jdbcUrl = "jdbc:postgresql://host:port/database?properties";
        var user = "user";
        var password = "password";

        var dbConfig = DataSourceConfig.builder()
              .driverClass(driverClass)
              .jdbcUrl(jdbcUrl)
              .user(user)
              .password(password)
              .build();

        var copy = DataSourceConfig.builder().copy(dbConfig).build();

        assertEquals(dbConfig.getDriverClass(), copy.getDriverClass());
        assertEquals(dbConfig.getJdbcUrl(), copy.getJdbcUrl());
        assertEquals(dbConfig.getUser(), copy.getUser());
        assertEquals(dbConfig.getPassword(), copy.getPassword());
    }
}
