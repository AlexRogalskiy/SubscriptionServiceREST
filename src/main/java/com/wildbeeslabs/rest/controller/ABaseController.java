package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.controller.proxy.IBaseProxyController;
import com.wildbeeslabs.rest.exception.EmptyContentException;
import com.wildbeeslabs.rest.model.IBaseEntity;
import com.wildbeeslabs.rest.model.dto.IBaseDTO;
import com.wildbeeslabs.rest.service.interfaces.IBaseService;

import java.beans.PropertyEditorSupport;

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
 * @param <E>
 */
public abstract class ABaseController<T extends IBaseEntity, E extends IBaseDTO> implements IBaseController<T, E> {

//    /**
//     * Default Logger instance
//     */
//    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(getProxyController().getAllItems(), HttpStatus.OK);
        } catch (EmptyContentException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<?> getById(final Long id) {
        return new ResponseEntity<>(getProxyController().getItemById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> create(final E itemDto) {
        E itemEntity = getProxyController().createItem(itemDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(final Long id, final E itemDto) {
        E itemEntity = getProxyController().updateItem(id, itemDto);
        return new ResponseEntity<>(itemEntity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(final Long id) {
        E itemEntity = getProxyController().deleteItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteAll() {
        getProxyController().deleteAllItems();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected static class BaseEnumConverter<U extends Enum<U>> extends PropertyEditorSupport {

        private final Class<U> type;

        public BaseEnumConverter(final Class<U> type) {
            this.type = type;
        }

        @Override
        public void setAsText(final String text) throws IllegalArgumentException {
            U item = Enum.valueOf(this.type, text.toUpperCase());
            setValue(item);
        }
    }

    protected abstract IBaseProxyController<T, E, ? extends IBaseService<T>> getProxyController();
}
