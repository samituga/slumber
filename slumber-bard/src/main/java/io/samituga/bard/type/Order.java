package io.samituga.bard.type;

import io.samituga.bard.filter.Precedence;
import io.samituga.slumber.heimer.type.Type;

public class Order extends Type<Integer> {

    private static final String ERROR_MESSAGE_INVALID_ORDER = "To set the max or min precedence use the io.samituga.bard.filter.Precedence initializer";

    private Order(Integer order) {
        super(order);
    }

    public static Order of(int order) {
        if (order == Integer.MAX_VALUE || order == Integer.MIN_VALUE) {
            throw new IllegalStateException(ERROR_MESSAGE_INVALID_ORDER);
        }
        return new Order(order);
    }

    public static Order of(Precedence precedence) {
        return new Order(precedence.precedenceLevel());
    }
}
