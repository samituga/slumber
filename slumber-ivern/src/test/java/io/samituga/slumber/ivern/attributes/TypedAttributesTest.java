package io.samituga.slumber.ivern.attributes;

import static io.samituga.slumber.ivern.attributes.TypedAttributes.TypedAttributesBuilder.attributesBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.samituga.slumber.ivern.attributes.exception.TypedAttributesException;
import org.junit.jupiter.api.Test;

public class TypedAttributesTest {

    @Test
    void should_get_value_of_string_type() {
        // given
        var attributes = attributesBuilder()
              .attribute("name", "John")
              .build();

        // when
        var name = attributes.get("name", String.class);

        // then
        assertThat(name).isEqualTo("John");
    }

    @Test
    void should_get_value_of_integer_type() {
        // given
        var attributes = attributesBuilder()
              .attribute("age", 30)
              .build();

        // when
        var age = attributes.get("age", Integer.class);

        // then
        assertThat(age).isEqualTo(30);
    }

    @Test
    void should_get_value_of_custom_type() {
        // given
        var value = new CustomType("test");
        var attributes = attributesBuilder()
              .attribute("custom", value)
              .build();

        // when
        var custom = attributes.get("custom", CustomType.class);

        // then
        assertThat(custom).isSameAs(value);
    }

    @Test
    void should_throw_exception_when_key_not_present() {
        // given
        var attributes = new TypedAttributes();

        // when then
        assertThatThrownBy(() -> attributes.get("missing", String.class))
              .isInstanceOf(TypedAttributesException.class)
              .hasMessage("Value for key 'missing' is not in attributes");
    }

    @Test
    void should_throw_exception_when_value_not_of_expected_type() {
        // given
        var attributes = attributesBuilder()
              .attribute("age", "30")
              .build();

        // when then
        assertThatThrownBy(() -> attributes.get("age", Integer.class))
              .isInstanceOf(TypedAttributesException.class)
              .hasMessage("Value for key 'age' is not of expected type Integer");
    }

    @Test
    void should_return_empty_optional_when_key_not_present() {
        // given
        var attributes = new TypedAttributes();

        // when
        var missing = attributes.find("missing", String.class);

        // then
        assertThat(missing).isEmpty();
    }

    @Test
    void should_return_optional_with_value_when_value_is_of_expected_type() {
        // given
        var attributes = attributesBuilder()
              .attribute("name", "John")
              .build();

        // when
        var name = attributes.find("name", String.class);

        // then
        assertThat(name).contains("John");
    }

    @Test
    void should_throw_exception_when_value_not_of_expected_type_in_find() {
        // given
        var attributes = attributesBuilder()
              .attribute("age", "30")
              .build();

        // when then
        assertThatThrownBy(() -> attributes.find("age", Integer.class))
              .isInstanceOf(TypedAttributesException.class)
              .hasMessage("Value for key 'age' is not of expected type Integer");
    }

    @Test
    void should_replace_existing_value_when_putting_with_existing_key() {
        // given
        var attributes = attributesBuilder()
              .attribute("name", "John")
              .build();

        // when
        var previousValue = attributes.put("name", "Jane");

        // then
        assertThat(previousValue).contains("John");
        assertThat(attributes.get("name", String.class)).isEqualTo("Jane");
    }

    @Test
    void should_add_new_value_when_putting_with_new_key() {
        // given
        var attributes = attributesBuilder()
              .attribute("name", "John")
              .build();

        // when
        var previousValue = attributes.put("age", 30);

        // then
        assertThat(previousValue).isEmpty();
        assertThat(attributes.get("age", Integer.class)).isEqualTo(30);
    }

    private record CustomType(String value) {
    }
}
