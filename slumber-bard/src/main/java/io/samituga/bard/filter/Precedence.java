package io.samituga.bard.filter;

public enum Precedence {
    FIRST(Integer.MIN_VALUE),
    LAST(Integer.MAX_VALUE),
    ;


    private final int precedenceLevel;

    Precedence(int precedenceLevel) {
        this.precedenceLevel = precedenceLevel;
    }

    public int precedenceLevel() {
        return precedenceLevel;
    }
}
