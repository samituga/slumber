package io.samituga.slumber.malz.jooq.config;

import static io.samituga.slumber.malz.driver.Driver.POSTGRES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.samituga.slumber.heimer.validator.exception.AssertionException;
import io.samituga.slumber.malz.driver.Driver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class DataSourceConfigTest {


    @Test
    void should_create_instance_when_arguments_are_valid() {
        // given
        var driverClass = POSTGRES;
        var jdbcUrl = "jdbc:postgresql://host:port/database?properties";
        var user = "user";
        var password = "password";

        // when
        var dbConfig = DataSourceConfigBuilder.builder()
              .driverClass(driverClass)
              .jdbcUrl(jdbcUrl)
              .user(user)
              .password(password)
              .build();

        // then
        assertThat(dbConfig.driverClass()).isEqualTo(driverClass);
        assertThat(dbConfig.jdbcUrl()).isEqualTo(jdbcUrl);
        assertThat(dbConfig.user()).isEqualTo(user);
        assertThat(dbConfig.password()).isEqualTo(password);
    }

    @ParameterizedTest
    @MethodSource("io.samituga.slumber.malz.jooq.config.DataSourceConfigDataProvider#constructor_parameters_with_invalid_arguments")
    void should_fail_validation_when_mandatory_fields_are_invalid(Driver driverClass,
                                                                  String jdbcUrl,
                                                                  String user,
                                                                  String password) {
        // given when then
        assertThatThrownBy(() ->
              DataSourceConfigBuilder.builder()
                    .driverClass(driverClass)
                    .jdbcUrl(jdbcUrl)
                    .user(user)
                    .password(password)
                    .build())
              .isInstanceOf(AssertionException.class);
    }
}
