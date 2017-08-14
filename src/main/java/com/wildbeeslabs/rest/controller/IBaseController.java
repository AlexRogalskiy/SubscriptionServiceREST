package com.wildbeeslabs.rest.controller;

import org.springframework.http.ResponseEntity;

/**
 *
 * Base REST Controller declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
public interface IBaseController<T> {

    ResponseEntity<?> getAll();

    ResponseEntity<?> getById(final Long id);

    ResponseEntity<?> create(final T item);

    ResponseEntity<?> update(final Long id, final T item);

    ResponseEntity<?> delete(final Long id);

    ResponseEntity<?> deleteAll();
}
