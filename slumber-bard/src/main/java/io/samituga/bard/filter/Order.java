package io.samituga.bard.filter;

public class Order {

    private static final String ERROR_MESSAGE_INVALID_ORDER = "To set the max or min precedence use the io.samituga.bard.filter.Precedence initializer";

    private final int value;

    private Order(int value) {
        this.value = value;
    }

    public int value() {
        return value;
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
