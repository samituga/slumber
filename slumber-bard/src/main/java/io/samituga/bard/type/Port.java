package io.samituga.bard.type;

import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredValidPort;

import io.samituga.slumber.ivern.type.Type;

public class Port extends Type<Integer> {

    public Port(int port) {
        super(requiredValidPort(port));
    }

    public static Port of(int port) {
        return new Port(port);
    }
}
