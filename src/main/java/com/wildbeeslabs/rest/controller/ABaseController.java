package com.wildbeeslabs.rest.controller;

//import com.wildbeeslabs.rest.exception.EmptyContentException;
import com.wildbeeslabs.rest.controller.proxy.IBaseProxyController;
import com.wildbeeslabs.rest.model.BaseEntity;
import com.wildbeeslabs.rest.model.IBaseEntity;
import com.wildbeeslabs.rest.model.dto.BaseDTO;
import com.wildbeeslabs.rest.model.dto.BaseDTOListWrapper;
//import com.wildbeeslabs.rest.model.dto.BaseDTOListWrapper;
import com.wildbeeslabs.rest.model.dto.converter.DTOConverter;
import com.wildbeeslabs.rest.model.dto.IBaseDTO;
import com.wildbeeslabs.rest.model.dto.IBaseDTOListWrapper;

import java.beans.PropertyEditorSupport;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.wildbeeslabs.rest.service.interfaces.IBaseService;
//import org.springframework.web.util.UriComponentsBuilder;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;

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

    /**
     * Default Logger instance
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

//    @Autowired
//    private IBaseProxyController<? extends T, ? extends E> proxyController;
//    @Autowired
//    private MessageSource messageSource;
    @Autowired
    private DTOConverter dtoConverter;

    @Override
    public ResponseEntity<?> getAll() {
        List<? extends T> items = getProxyController().getAllItems();
        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(getDTOConverter().convertToDTOAndWrap(items, getDtoClass(), getDtoListClass()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(final Long id) {
        T item = getProxyController().getItemById(id);
        return new ResponseEntity<>(getDTOConverter().convertToDTO(item, getDtoClass()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> create(final E itemDto) {
        T itemEntity = getProxyController().createItem(itemDto, getEntityClass());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(final Long id, final E itemDto) {
        T itemEntity = getProxyController().updateItem(id, itemDto, getEntityClass());
        return new ResponseEntity<>(getDTOConverter().convertToDTO(itemEntity, getDtoClass()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(final Long id) {
        T itemEntity = getProxyController().deleteItem(id);
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

    protected Class<? extends T> getEntityClass() {
        return (Class<? extends T>) BaseEntity.class;
    }

    protected Class<? extends E> getDtoClass() {
        return (Class<? extends E>) BaseDTO.class;
    }

    protected abstract IBaseProxyController<T, E, ? extends IBaseService<T>> getProxyController();
    //protected abstract IBaseService<T> getDefaultService();

    protected Class<? extends IBaseDTOListWrapper> getDtoListClass() {
        return BaseDTOListWrapper.class;
    }

    protected DTOConverter getDTOConverter() {
        return this.dtoConverter;
    }
}
