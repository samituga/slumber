package io.samituga.slumber.malz.model;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

public abstract class Entity<ID> {

    private final ID id;

    protected Entity(ID id) {
        this.id = required("id", id);
    }

    public ID id() {
        return id;
    }
}
