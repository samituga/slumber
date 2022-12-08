package io.samituga.slumber.malz.repository.operation;

import io.samituga.slumber.malz.error.SQLQueryError;
import io.samituga.slumber.malz.model.Entity;
import java.util.Collection;
import java.util.Optional;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SelectWhereStep;
import org.jooq.exception.TooManyRowsException;

public class DatabaseOperation {

    public static <ID, E extends Entity<ID>, R extends Record>
    Optional<E> findOneWhere(SelectWhereStep<R> select,
                             Condition condition,
                             RecordMapper<R, E> mapper) {
        try {
            return select.where(condition).fetchOptional().map(mapper);
        } catch (TooManyRowsException e) {
            throw new SQLQueryError(condition);
        }
    }

    public static <ID, E extends Entity<ID>, R extends Record>
    Collection<E> findAllWhere(SelectWhereStep<R> select,
                               Condition condition,
                               RecordMapper<R, E> mapper) {
        return select.where(condition).fetch().map(mapper);
    }
}
