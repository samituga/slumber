package io.samituga.slumber.repository.command;

import org.jooq.DSLContext;
import org.jooq.DeleteUsingStep;
import org.jooq.InsertSetStep;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.Table;
import org.jooq.UpdateSetStep;

public class DatabaseCommandsImpl implements DatabaseCommand {

    private final DSLContext dslContext;

    public DatabaseCommandsImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }


    public <R extends Record> InsertSetStep<R> add(Table<R> table) {
        return dslContext.insertInto(table);
    }

    public <R extends Record> SelectWhereStep<R> select(Table<R> table) {
        return dslContext.selectFrom(table);
    }

    public <R extends Record> UpdateSetStep<R> update(Table<R> table) {
        return dslContext.update(table);
    }

    public <R extends Record> DeleteUsingStep<R> delete(Table<R> table) {
        return dslContext.delete(table);
    }
}
