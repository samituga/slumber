package io.samituga.slumber.malz.jooq.config;


import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

import static io.samituga.slumber.malz.driver.Driver.POSTGRES;

public class DataSourceConfigDataProvider {

    static Stream<Arguments> constructor_parameters_with_invalid_arguments() {

        var invalidDriverClass1 = Arguments.of(
              null,
              "jdbc:postgresql://host:port/database?properties",
              "user",
              "password"
        );

        var invalidJdbcUrl1 = Arguments.of(
              POSTGRES,
              "",
              "user",
              "password"
        );
        var invalidJdbcUrl2 = Arguments.of(
              POSTGRES,
              "    ",
              "user",
              "password"
        );
        var invalidJdbcUrl3 = Arguments.of(
              POSTGRES,
              null,
              "user",
              "password"
        );

        var invalidUser1 = Arguments.of(
              POSTGRES,
              "jdbc:postgresql://host:port/database?properties",
              "",
              "password"
        );
        var invalidUser2 = Arguments.of(
              POSTGRES,
              "jdbc:postgresql://host:port/database?properties",
              "    ",
              "password"
        );
        var invalidUser3 = Arguments.of(
              POSTGRES,
              "jdbc:postgresql://host:port/database?properties",
              null,
              "password"
        );

        var invalidPassword1 = Arguments.of(
              POSTGRES,
              "jdbc:postgresql://host:port/database?properties",
              "user",
              ""
        );
        var invalidPassword2 = Arguments.of(
              POSTGRES,
              "jdbc:postgresql://host:port/database?properties",
              "user",
              "    "
        );
        var invalidPassword3 = Arguments.of(
              POSTGRES,
              "jdbc:postgresql://host:port/database?properties",
              "user",
              null
        );

        return Stream.of(
              invalidDriverClass1,
              invalidJdbcUrl1,
              invalidJdbcUrl2,
              invalidJdbcUrl3,
              invalidUser1,
              invalidUser2,
              invalidUser3,
              invalidPassword1,
              invalidPassword2,
              invalidPassword3
        );
    }

}
