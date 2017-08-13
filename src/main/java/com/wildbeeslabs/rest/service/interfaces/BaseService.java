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

    static enum DateTypeOrder {
        BEFORE, AFTER;
    }

    static enum SortTypeOrder {
        ASC, DESC;
    }

    T findById(final Long id);

    void save(final T item);

    void update(final T item);

    void deleteById(final Long id);

    List<T> findAll();

    void deleteAll();

    boolean isExist(final T item);
}
