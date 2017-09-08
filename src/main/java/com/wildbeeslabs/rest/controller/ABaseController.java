package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.exception.ResourceAlreadyExistException;
import com.wildbeeslabs.rest.exception.ResourceNotFoundException;
import com.wildbeeslabs.rest.model.BaseEntity;
import com.wildbeeslabs.rest.model.dto.BaseDTO;
import com.wildbeeslabs.rest.model.dto.BaseDTOListWrapper;
import com.wildbeeslabs.rest.service.interfaces.BaseService;

import java.beans.PropertyEditorSupport;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

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
 * @param <E>
 */
public abstract class ABaseController<T extends BaseEntity, E extends BaseDTO> implements IBaseController<T, E> {

    /**
     * Default Logger instance
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MessageSource messageSource;
    @Autowired
    protected ModelMapper modelMapper;

    protected String getLocaleMessage(final String message) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, null, locale);
    }

    @Override
    public ResponseEntity<?> getAll() {
        LOGGER.info("Fetching all items");
        List<T> items = getDefaultService().findAll();
        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //return new ResponseEntity<>(items.stream().map(item -> convertToDTO(item, getDtoClass())).collect(Collectors.toList()), HttpStatus.OK);
        return new ResponseEntity<>(convertToDTOList(items, getDtoClass(), getDtoListClass()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(final Long id) {
        LOGGER.info("Fetching item by id {}", id);
        T item = getDefaultService().findById(id);
        if (Objects.isNull(item)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.item.id"), id));
        }
        return new ResponseEntity<>(convertToDTO(item, getDtoClass()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> create(final E itemDto) {
        LOGGER.info("Creating item {}", itemDto);
        T itemEntity = convertToEntity(itemDto, getEntityClass());
        if (getDefaultService().isExist(itemEntity)) {
            throw new ResourceAlreadyExistException(String.format(getLocaleMessage("error.already.exist.item")));
        }
        getDefaultService().save(itemEntity);
        return new ResponseEntity<>(HttpStatus.OK);
        /*
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(id).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
         */
    }

    @Override
    public ResponseEntity<?> update(final Long id, final E itemDto) {
        LOGGER.info("Updating item by id {}", id);
        T currentItem = getDefaultService().findById(id);
        if (Objects.isNull(currentItem)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.item.id"), id));
        }
        T itemEntity = convertToEntity(itemDto, getEntityClass());
        getDefaultService().merge(currentItem, itemEntity);
        return new ResponseEntity<>(convertToDTO(currentItem, getDtoClass()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(final Long id) {
        LOGGER.info("Deleting item by id {}", id);
        T item = getDefaultService().findById(id);
        if (Objects.isNull(item)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.item.id"), id));
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

    protected static class BaseEnumConverter<U extends Enum<U>> extends PropertyEditorSupport {

        private final Class<U> type;

        public BaseEnumConverter(final Class<U> type) {
            this.type = type;
        }

        @Override
        public void setAsText(final String text) throws IllegalArgumentException {
            String capitalized = text.toUpperCase();
            U item = Enum.valueOf(this.type, capitalized);
            setValue(item);
        }
    }

//    protected E convertToDTO(final T itemEntity, final Class<? extends E> clazz) {
//        E itemDto = modelMapper.map(itemEntity, clazz);
//        return itemDto;
//        //return this.convertToDTO(itemEntity, clazz);
//    }
    protected <M, N> N convertToDTO(final M itemEntity, final Class<? extends N> clazz) {
        N itemDto = modelMapper.map(itemEntity, clazz);
        return itemDto;
    }

    protected <M, N> BaseDTOListWrapper<? extends N> convertToDTOList(final List<? extends M> itemEntityList, final Class<? extends N> clazz, final Class<? extends BaseDTOListWrapper> classListWrapper) {
        List<? extends N> itemDtoList = itemEntityList.stream()
                .map(item -> convertToDTO(item, clazz))
                .collect(Collectors.toCollection(LinkedList::new));
        BaseDTOListWrapper<N> listWrapper = null;
        try {
            listWrapper = classListWrapper.newInstance();
            listWrapper.setItems(itemDtoList);
        } catch (InstantiationException | IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ABaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listWrapper;
    }

//    protected T convertToEntity(final E itemDto, final Class<? extends T> clazz) {
//        T itemEntity = modelMapper.map(itemDto, clazz);
//        return itemEntity;
//    }
    protected <N, M> M convertToEntity(final N itemDto, final Class<? extends M> clazz) {
        M itemEntity = modelMapper.map(itemDto, clazz);
        return itemEntity;
    }

    protected <N, M> List<? extends M> convertToEntityList(final BaseDTOListWrapper<? extends N> itemDtoList, final Class<? extends M> clazz) {
        List<M> itemEntityList = itemDtoList.getItems().stream()
                .map(item -> convertToEntity(item, clazz))
                .collect(Collectors.toCollection(LinkedList::new));
        return itemEntityList;
    }

    protected abstract Class<? extends T> getEntityClass();

    protected abstract Class<? extends E> getDtoClass();

    protected Class<? extends BaseDTOListWrapper> getDtoListClass() {
        return BaseDTOListWrapper.class;
    }
}
