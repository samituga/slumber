package io.samituga.slumber.ivern.attributes;

import io.samituga.slumber.ivern.attributes.exception.TypedAttributesException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TypedAttributes {

    private static final String NULL_ERROR_MESSAGE = "Value for key '%s' is not in attributes";
    private static final String CAST_ERROR_MESSAGE = "Value for key '%s' is not of expected type %s";
    private final Map<String, Object> attributes;

    public TypedAttributes() {
        this.attributes = new HashMap<>();
    }

    public TypedAttributes(Map<String, Object> attributes) {
        this.attributes = new HashMap<>(attributes);
    }


    public <T> Optional<Object> put(String key, T value) {
        return Optional.ofNullable(attributes.put(key, value));
    }

    public <T> T get(String key, Class<T> clazz) {
        var value = this.attributes.get(key);
        if (value == null) {
            throw new TypedAttributesException(String.format(NULL_ERROR_MESSAGE, key));
        }
        if (!clazz.isInstance(value)) {
            throw new TypedAttributesException(
                  String.format(CAST_ERROR_MESSAGE, key, clazz.getSimpleName()));
        }
        return clazz.cast(value);
    }

    public <T> Optional<T> find(String key, Class<T> clazz) {
        var value = this.attributes.get(key);
        if (value == null) {
            return Optional.empty();
        }
        if (!clazz.isInstance(value)) {
            throw new TypedAttributesException(
                  String.format(CAST_ERROR_MESSAGE, key, clazz.getSimpleName()));
        }
        return Optional.of(clazz.cast(value));
    }


    public static final class TypedAttributesBuilder {
        private final Map<String, Object> attributes = new HashMap<>();

        private TypedAttributesBuilder() {
        }

        public static TypedAttributesBuilder attributesBuilder() {
            return new TypedAttributesBuilder();
        }

        public TypedAttributesBuilder attribute(String key, Object value) {
            attributes.put(key, value);
            return this;
        }

        public TypedAttributes build() {
            return new TypedAttributes(attributes);
        }
    }
}
