package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.exception.ResourceAlreadyExistException;
import com.wildbeeslabs.rest.exception.ResourceNotFoundException;
import com.wildbeeslabs.rest.model.BaseEntity;
import com.wildbeeslabs.rest.service.interfaces.BaseService;

import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

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
     * Default Logger instance
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageSource messageSource;

    protected String getMessage(final String name) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(name, null, locale);
    }

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
            throw new ResourceNotFoundException(String.format(getMessage("error.no.item.id"), id));
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> create(final T item) {
        LOGGER.info("Creating item : {}", item);
        if (getDefaultService().isExist(item)) {
            throw new ResourceAlreadyExistException(String.format(getMessage("error.already.exist.item")));
        }
        getDefaultService().save(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(final Long id, final T item) {
        LOGGER.info("Updating item by id {}", id);
        T currentItem = getDefaultService().findById(id);
        if (Objects.isNull(currentItem)) {
            throw new ResourceNotFoundException(String.format(getMessage("error.no.item.id"), id));
        }
        getDefaultService().merge(currentItem, item);
        return new ResponseEntity<>(currentItem, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(final Long id) {
        LOGGER.info("Deleting item by id {}", id);
        T item = getDefaultService().findById(id);
        if (Objects.isNull(item)) {
            throw new ResourceNotFoundException(String.format(getMessage("error.no.item.id"), id));
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
