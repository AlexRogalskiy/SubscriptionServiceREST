package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.exception.ServiceException;
import com.wildbeeslabs.rest.model.BaseEntity;
import com.wildbeeslabs.rest.service.interfaces.BaseService;

import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * Abstract Base REST Controller implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
public abstract class AbscractBaseController<T extends BaseEntity> implements IBaseController<T> {

    /**
     * Default logger instance
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<?> getAll() {
        LOGGER.info("Fetching all items");
        List<T> items = getDefaultService().findAll();
        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(final Long id) {
        LOGGER.info("Fetching item by id {}", id);
        T item = getDefaultService().findById(id);
        if (Objects.isNull(item)) {
            String errorMessage = String.format("ERROR: item with id={%d} is not found", id);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> create(final T item) {
        LOGGER.info("Creating item : {}", item);
        if (getDefaultService().isExist(item)) {
            String errorMessage = String.format("ERROR: item already exist");
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.CONFLICT);
        }
        getDefaultService().save(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(final Long id, final T item) {
        LOGGER.info("Updating item by id {}", id);
        T currentItem = getDefaultService().findById(id);
        if (Objects.isNull(currentItem)) {
            String errorMessage = String.format("ERROR: item with id={%d} is not found", id);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        getDefaultService().merge(currentItem, item);
        return new ResponseEntity<>(currentItem, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(final Long id) {
        LOGGER.info("Deleting item by id {}", id);
        T item = getDefaultService().findById(id);
        if (Objects.isNull(item)) {
            String errorMessage = String.format("ERROR: item with id={%d} is not found", id);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        getDefaultService().deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteAll() {
        LOGGER.info("Deleting all items");
        getDefaultService().deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected abstract BaseService<T> getDefaultService();

    protected static class BaseEnumConverter<T extends Enum<T>> extends PropertyEditorSupport {

        private final Class<T> type;

        public BaseEnumConverter(final Class<T> type) {
            this.type = type;
        }

        @Override
        public void setAsText(final String text) throws IllegalArgumentException {
            String capitalized = text.toUpperCase();
            T item = Enum.valueOf(this.type, capitalized);
            setValue(item);
        }
    }
}
