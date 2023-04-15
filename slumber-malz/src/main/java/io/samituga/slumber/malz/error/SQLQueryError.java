package io.samituga.slumber.malz.error;

import static io.samituga.slumber.malz.error.message.SQLQueryErrorMessage.FIND_ONE_ERROR;

public class SQLQueryError extends Error {


    public SQLQueryError(String errorMessage) { // TODO: 01/01/2023 Generalize
        super(errorMessage); // TODO: 01/01/2023 Why FIND_ONE_ERROR?
    }

    public SQLQueryError(String condition, Throwable throwable) { // TODO: 01/01/2023 Generalize
        super(String.format(FIND_ONE_ERROR.format(), condition), throwable);  // TODO: 01/01/2023 Why FIND_ONE_ERROR?
    }
}
