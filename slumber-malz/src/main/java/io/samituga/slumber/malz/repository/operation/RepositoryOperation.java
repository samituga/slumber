package io.samituga.slumber.malz.repository.operation;

import static io.samituga.slumber.heimer.validator.Validator.required;
import static io.samituga.slumber.heimer.validator.Validator.requiredNotEmpty;

import io.samituga.slumber.malz.error.SQLQueryError;
import io.samituga.slumber.malz.repository.Repository;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jooq.Condition;
import org.jooq.ConnectionProvider;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.UpdateQuery;
import org.jooq.exception.TooManyRowsException;

public abstract class RepositoryOperation<R extends Record> extends Repository {

    protected final Table<R> table;

    public RepositoryOperation(ConnectionProvider connectionProvider, Table<R> table) {
        super(connectionProvider);
        this.table = required("table", table);
    }

    protected Optional<R> findOneWhere(Condition condition) {
        required("condition", condition);

        try {
            return dslContext.selectFrom(table).where(condition).fetchOptional();
        } catch (TooManyRowsException e) {
            throw new SQLQueryError(condition);
        }
    }

    protected Collection<R> findAllFrom(int limit) {
        return dslContext.selectFrom(table).limit(limit).fetch();
    }

    protected Collection<R> findAllWhere(Condition condition) {
        return findAllWhere(condition, 0);
    }

    protected Collection<R> findAllWhere(Condition condition, int limit) {
        required("condition", condition);

        return dslContext.selectFrom(table).where(condition).limit(limit).fetch();
    }

    protected boolean updateWhere(Condition condition, R record) {
        required("condition", condition);
        required("record", record);

        final var updatedRows = dslContext.update(table).set(record).where(condition)
              .execute();
        return shouldUpdateOnlyOneRow(updatedRows, condition);
    }

    protected int updateAllWhere(Map<R, Condition> recordsWithCondition) {
        requiredNotEmpty("recordsWithCondition", recordsWithCondition);

        final var updateQueries = updateQueries(recordsWithCondition);

        final var updatedRowsForRecord = dslContext.batch(updateQueries).execute();

        var totalRowsUpdated = 0;

        final var iterator = recordsWithCondition.values().iterator();

        for (int updatedRows : updatedRowsForRecord) {
            shouldUpdateOnlyOneRow(updatedRows, iterator.next());
            totalRowsUpdated += updatedRows;
        }

        return totalRowsUpdated;
    }

    private boolean shouldUpdateOnlyOneRow(int updatedRows, Condition condition) {
        if (updatedRows > 1) {
            throw new SQLQueryError(condition);
        }
        return updatedRows > 0;
    }

    private List<UpdateQuery<R>> updateQueries(Map<R, Condition> records) {
        return records.entrySet().stream().map(entry -> {
            final var updateQuery = dslContext.updateQuery(table);
            updateQuery.setRecord(entry.getKey());
            updateQuery.addConditions(entry.getValue());
            return updateQuery;
        }).toList();
    }
}
