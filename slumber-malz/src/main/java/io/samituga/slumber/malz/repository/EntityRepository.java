package io.samituga.slumber.malz.repository;

import io.samituga.slumber.malz.model.Entity;
import java.util.Collection;
import java.util.Optional;

public interface EntityRepository<E extends Entity<ID>, ID> {


    Optional<E> find(ID id);

    Collection<E> findAll();

    Collection<E> findAll(int limit);

    Collection<E> findAllIn(Collection<ID> ids);

    Collection<E> findAllIn(Collection<ID> ids, int limit);


    boolean update(E entity);

    int updateAll(Collection<E> entities);

    boolean delete(ID id);

    boolean delete(E entity);

    Collection<E> deleteAll(Collection<E> entities);

    boolean exists(ID id);
}
