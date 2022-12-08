package io.samituga.slumber.malz.error;

import static io.samituga.slumber.malz.error.message.SQLQueryErrorMessage.FIND_ONE_ERROR;

import org.jooq.Condition;

public class SQLQueryError extends Error {


    public SQLQueryError(Condition condition) {
        super(String.format(FIND_ONE_ERROR.format(), condition.toString()));
    }

    public SQLQueryError(Condition condition, Throwable throwable) {
        super(String.format(FIND_ONE_ERROR.format(), condition.toString()), throwable);
    }
}
