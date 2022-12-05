package io.samituga.slumber.model;

import static io.samituga.slumber.validator.Validator.required;

public abstract class Entity<ID> {

    public final ID id;

    protected Entity(ID id) {
        this.id = required("id", id);
    }
}
