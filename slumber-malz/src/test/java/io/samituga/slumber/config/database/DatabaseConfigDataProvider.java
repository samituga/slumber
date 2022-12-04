package io.samituga.slumber.config.database;

import static org.jooq.SQLDialect.POSTGRES;

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class DatabaseConfigDataProvider {

    static Stream<Arguments> constructor_parameters_with_invalid_arguments() {

        var invalidDriverClass1 = Arguments.of(
              "",
              "jdbc:postgresql://host:port/database?properties",
              "user",
              "password"
        );
        var invalidDriverClass2 = Arguments.of(
              "    ",
              "jdbc:postgresql://host:port/database?properties",
              "user",
              "password"
        );
        var invalidDriverClass3 = Arguments.of(
              null,
              "jdbc:postgresql://host:port/database?properties",
              "user",
              "password"
        );

        var invalidJdbcUrl1 = Arguments.of(
              POSTGRES.getName(),
              "",
              "user",
              "password"
        );
        var invalidJdbcUrl2 = Arguments.of(
              POSTGRES.getName(),
              "    ",
              "user",
              "password"
        );
        var invalidJdbcUrl3 = Arguments.of(
              POSTGRES.getName(),
              null,
              "user",
              "password"
        );

        var invalidUser1 = Arguments.of(
              POSTGRES.getName(),
              "jdbc:postgresql://host:port/database?properties",
              "",
              "password"
        );
        var invalidUser2 = Arguments.of(
              POSTGRES.getName(),
              "jdbc:postgresql://host:port/database?properties",
              "    ",
              "password"
        );
        var invalidUser3 = Arguments.of(
              POSTGRES.getName(),
              "jdbc:postgresql://host:port/database?properties",
              null,
              "password"
        );

        var invalidPassword1 = Arguments.of(
              POSTGRES.getName(),
              "jdbc:postgresql://host:port/database?properties",
              "user",
              ""
        );
        var invalidPassword2 = Arguments.of(
              POSTGRES.getName(),
              "jdbc:postgresql://host:port/database?properties",
              "user",
              "    "
        );
        var invalidPassword3 = Arguments.of(
              POSTGRES.getName(),
              "jdbc:postgresql://host:port/database?properties",
              "user",
              null
        );

        return Stream.of(
              invalidDriverClass1,
              invalidDriverClass2,
              invalidDriverClass3,
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
