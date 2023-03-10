package io.samituga.slumber.ivern.type;

import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredNotEmpty;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public abstract class MapType<K, V> extends Type<Map<K, V>> {

    protected MapType() {
        super(Collections.emptyMap());
    }

    protected MapType(Map<K, V> value) {
        super(requiredNotEmpty("value", Map.copyOf(value)));
    }

    public V get(K key) {
        return Objects.requireNonNull(value().get(key));
    }

    public Optional<V> find(K key) {
        return Optional.ofNullable(value().get(key));
    }

    public final int size() {
        return value().size();
    }

    public final boolean isEmpty() {
        return value().isEmpty();
    }
}
