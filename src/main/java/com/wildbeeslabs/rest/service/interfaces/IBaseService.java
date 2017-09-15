package com.wildbeeslabs.rest.service.interfaces;

import java.io.Serializable;
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
public interface IBaseService<T extends Serializable> {

    static enum DateTypeOrder {
        BEFORE, AFTER;
    }

    static enum SortTypeOrder {
        ASC, DESC;
    }

    default T findById(final Long id) {
        return null;
    }

    void save(final T item);

    void update(final T item);

    void merge(final T itemTo, final T itemFrom);

    default void deleteById(final Long id) {
    }

    ;

    void delete(final T item);

    void delete(final List<? extends T> item);

    List<T> findAll();

    void deleteAll();

    boolean isExist(final T item);
}
