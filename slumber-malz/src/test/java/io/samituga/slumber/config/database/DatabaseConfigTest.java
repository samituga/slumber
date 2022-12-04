package io.samituga.slumber.config.database;

import static org.jooq.SQLDialect.POSTGRES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.samituga.slumber.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class DatabaseConfigTest {


    @Test
    void shouldCreateInstanceWhenArgumentsAreValid() {
        var driverClass = POSTGRES.getName();
        var jdbcUrl = "jdbc:postgresql://host:port/database?properties";
        var user = "user";
        var password = "password";

        var dbConfig = DatabaseConfig.builder()
              .driverClass(driverClass)
              .jdbcUrl(jdbcUrl)
              .user(user)
              .password(password)
              .build();

        assertEquals(driverClass, dbConfig.getDriverClass());
        assertEquals(jdbcUrl, dbConfig.getUrl());
        assertEquals(user, dbConfig.getUser());
        assertEquals(password, dbConfig.getPassword());
    }

    @ParameterizedTest
    @MethodSource("io.samituga.slumber.config.database.DatabaseConfigDataProvider#constructor_parameters_with_invalid_arguments")
    void should_fail_validation_when_mandatory_fields_are_invalid(String driverClass,
                                                                  String jdbcUrl,
                                                                  String user,
                                                                  String password) {
        assertThrows(ValidationException.class,
              () -> DatabaseConfig.builder()
                    .driverClass(driverClass)
                    .jdbcUrl(jdbcUrl)
                    .user(user)
                    .password(password)
                    .build());
    }
}
