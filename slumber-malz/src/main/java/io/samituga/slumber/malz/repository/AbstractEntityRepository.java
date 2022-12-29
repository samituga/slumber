package io.samituga.slumber.malz.repository;

import static io.samituga.slumber.heimer.validator.AssertionUtility.required;
import static io.samituga.slumber.heimer.validator.AssertionUtility.requiredNotEmpty;
import static java.util.stream.Collectors.toMap;

import io.samituga.slumber.malz.model.Entity;
import io.samituga.slumber.malz.repository.operation.RepositoryOperation;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jooq.ConnectionProvider;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;

public abstract class AbstractEntityRepository<ID, E extends Entity<ID>, R extends Record> extends
      RepositoryOperation<R> implements EntityRepository<E, ID> {

    private final TableField<R, ID> idTableField;

    public AbstractEntityRepository(ConnectionProvider connectionProvider, Table<R> table,
                                    TableField<R, ID> idTableField) {
        super(connectionProvider, table);
        this.idTableField = required("idTableField", idTableField);
    }

    @Override
    public Optional<E> find(ID id) {
        required("id", id);
        return findOneWhere(idTableField.eq(id)).map(this::toEntity);
    }

    @Override
    public Collection<E> findAll() {
        return findAll(Integer.MAX_VALUE);
    }

    @Override
    public Collection<E> findAll(int limit) {
        return toEntity(findAllFrom(limit));
    }

    @Override
    public Collection<E> findAllIn(Collection<ID> ids) {
        return toEntity(findAllWhere(idTableField.in(requiredNotEmpty("ids", ids))));
    }

    @Override
    public Collection<E> findAllIn(Collection<ID> ids, int limit) {
        return toEntity(findAllWhere(idTableField.in(requiredNotEmpty("ids", ids)), limit));
    }

    @Override
    public boolean update(E entity) {
        required("entity", entity);

        return updateWhere(idTableField.eq(entity.id), toRecord(entity));
    }

    @Override
    public int updateAll(Collection<E> entities) {
        requiredNotEmpty("entities", entities);

        return updateAllWhere(
              entities.stream().collect(toMap(this::toRecord, e -> idTableField.eq(e.id))));
    }

    @Override
    public boolean delete(ID id) {
        required("id", id);
        return deleteWhere(idTableField.eq(id));
    }

    @Override
    public boolean delete(E entity) {
        required("entity", entity);

        return delete(entity.id);
    }

    @Override
    public boolean deleteAll(Collection<E> entities) {
        requiredNotEmpty("entities", entities);

        final var ids = entities.stream().map(e -> e.id).collect(Collectors.toSet());
        return deleteAllWhere(idTableField.in(ids), ids.size());
    }

    @Override
    public boolean exists(ID id) {
        required("id", id);

        return dslContext.fetchExists(table, idTableField.eq(id));
    }

    private Collection<E> toEntity(Collection<R> records) {
        return records.stream().map(this::toEntity).toList();
    }

    private Collection<R> toRecord(Collection<E> entities) {
        return entities.stream().map(this::toRecord).toList();
    }

    protected abstract E toEntity(R record);

    protected abstract R toRecord(E entity);
}
