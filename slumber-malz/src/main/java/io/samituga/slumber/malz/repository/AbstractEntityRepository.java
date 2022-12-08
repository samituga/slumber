package io.samituga.slumber.malz.repository;

import static io.samituga.slumber.heimer.validator.Validator.required;
import static io.samituga.slumber.malz.repository.operation.DatabaseOperation.findAllWhere;
import static io.samituga.slumber.malz.repository.operation.DatabaseOperation.findOneWhere;

import io.samituga.slumber.malz.model.Entity;
import java.util.Collection;
import java.util.Optional;
import org.jooq.ConnectionProvider;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;

public abstract class AbstractEntityRepository<ID, E extends Entity<ID>, R extends Record> extends
      Repository implements EntityRepository<E, ID> {

    private final Table<R> table;
    private final TableField<R, ID> idTableField;

    public AbstractEntityRepository(ConnectionProvider connectionProvider,
                                    Table<R> table,
                                    TableField<R, ID> idTableField) {
        super(connectionProvider);
        this.table = required("table", table);
        this.idTableField = required("idTableField", idTableField);
    }

    // TODO: 2022-12-05 implementations
    @Override
    public Optional<E> find(ID id) {
        return findOneWhere(dslContext.selectFrom(table), idTableField.eq(id), this::toEntity);
    }

    @Override
    public Collection<E> findAll(int limit) {
        return dslContext.selectFrom(table)
              .limit(limit)
              .fetch()
              .map(this::toEntity);
    }

    @Override
    public Collection<E> findAllIn(Collection<ID> ids) {
        return findAllWhere(dslContext.selectFrom(table), idTableField.in(ids), this::toEntity);
    }

    @Override
    public E update(E entity) {
        return null;
    }

    @Override
    public Collection<E> updateAll(Collection<E> entities) {
        return null;
    }

    @Override
    public boolean delete(ID id) {
        return false;
    }

    @Override
    public Collection<E> deleteAll(Collection<E> entities) {
        return null;
    }

    @Override
    public boolean exists(ID id) {
        return dslContext.fetchExists(table, idTableField.eq(id));
    }

    protected abstract E toEntity(R record);

    protected abstract R toRecord(E entity);
}
