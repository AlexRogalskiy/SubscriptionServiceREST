package com.wildbeeslabs.rest.service.interfaces;

import java.util.List;

/**
 *
 * Base REST Application Service declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
public interface BaseService<T> {

    T findById(long id);

    T findByName(final String name);

    void save(final T item);

    void update(final T item);

    void deleteById(long id);

    List<T> findAll();

    void deleteAll();

    boolean isExist(final T item);
}
