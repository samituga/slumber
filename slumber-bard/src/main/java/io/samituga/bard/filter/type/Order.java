package io.samituga.bard.filter.type;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;

import io.samituga.bard.filter.Precedence;
import io.samituga.slumber.heimer.type.Type;
import java.util.Objects;

public class Order extends Type<Integer> implements Comparable<Order>{

    private static final String ERROR_MESSAGE_INVALID_ORDER = "To set the max or min precedence use the io.samituga.bard.filter.Precedence initializer";


    private Order(Integer order) {
        super(required("order", order));
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

    @Override
    public int compareTo(Order other) {
        return this.value().compareTo(other.value());
    }
}
