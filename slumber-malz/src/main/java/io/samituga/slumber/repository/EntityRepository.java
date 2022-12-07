package io.samituga.slumber.repository;

import io.samituga.slumber.model.Entity;
import java.util.Collection;
import java.util.Optional;

public interface EntityRepository<E extends Entity<ID>, ID> {


    Optional<E> find(ID id);

    Collection<E> findAll(int limit);

    Collection<E> findAllIn(Collection<ID> ids);

    E update(E entity);

    Collection<E> updateAll(Collection<E> entities);

    boolean delete(ID id);

    Collection<E> deleteAll(Collection<E> entities);

    boolean exists(ID id);
}
