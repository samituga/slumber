package io.samituga.slumber.repository;

import io.samituga.slumber.model.Entity;
import org.jooq.ConnectionProvider;
import org.jooq.Record;
import org.jooq.Table;
import java.util.Collection;
import java.util.Optional;

public class AbstractEntityRepository<E extends Entity<ID>, ID, R extends Record> extends Repository implements EntityRepository<E, ID>{

    private final Table<R> table;

    public AbstractEntityRepository(ConnectionProvider connectionProvider, Table<R> table) {
        super(connectionProvider);
        this.table = table;
    }

    // TODO: 2022-12-05 implementations
    @Override
    public Optional<E> find(ID id) {
        return Optional.empty();
    }

    @Override
    public Collection<E> findAll(Collection<ID> ids) {
        return null;
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
        return false;
    }
}
