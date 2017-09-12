/*
 * The MIT License
 *
 * Copyright 2017 Pivotal Software, Inc..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.rest.controller.proxy;

import com.wildbeeslabs.rest.exception.ResourceAlreadyExistException;
import com.wildbeeslabs.rest.exception.ResourceNotFoundException;
import com.wildbeeslabs.rest.model.BaseEntity;
import com.wildbeeslabs.rest.model.dto.BaseDTO;
import com.wildbeeslabs.rest.model.dto.DTOConverter;
import com.wildbeeslabs.rest.utils.ResourceUtils;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import com.wildbeeslabs.rest.service.interfaces.IBaseService;

/**
 *
 * Base Proxy REST Controller implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 * @param <E>
 * @param <S>
 */
public abstract class ABaseProxyController<T extends BaseEntity, E extends BaseDTO, S extends IBaseService<T>> implements IBaseProxyController<T, E, S> {

    /**
     * Default Logger instance
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private DTOConverter dtoConverter;
    @Autowired
    protected S service;

    @Override
    public List<? extends T> getAllItems() {
        LOGGER.info("Fetching all items");
        List<? extends T> items = getService().findAll();
//        if (items.isEmpty()) {
//            throw new EmptyContentException(String.format(getLocaleMessage("error.no.content")));
//        }
        return items;
    }

    @Override
    public T getItemById(final Long id) {
        LOGGER.info("Fetching item by id {}", id);
        T item = getService().findById(id);
        if (Objects.isNull(item)) {
            throw new ResourceNotFoundException(String.format(ResourceUtils.getLocaleMessage("error.no.item.id"), id));
        }
        return item;
    }

    @Override
    public T createItem(final E itemDto, Class<? extends T> entityClass) {
        LOGGER.info("Creating item {}", itemDto);
        T itemEntity = getDTOConverter().convertToEntity(itemDto, entityClass);
        if (getService().isExist(itemEntity)) {
            throw new ResourceAlreadyExistException(String.format(ResourceUtils.getLocaleMessage("error.already.exist.item")));
        }
        getService().save(itemEntity);
        return itemEntity;
    }

    @Override
    public T updateItem(final Long id, final E itemDto, Class<? extends T> entityClass) {
        LOGGER.info("Updating item by id {}", id);
        T currentItem = getService().findById(id);
        if (Objects.isNull(currentItem)) {
            throw new ResourceNotFoundException(String.format(ResourceUtils.getLocaleMessage("error.no.item.id"), id));
        }
        T itemEntity = getDTOConverter().convertToEntity(itemDto, entityClass);
        getService().merge(currentItem, itemEntity);
        return currentItem;
    }

    @Override
    public T deleteItem(final Long id) {
        LOGGER.info("Deleting item by id {}", id);
        T item = getService().findById(id);
        if (Objects.isNull(item)) {
            throw new ResourceNotFoundException(String.format(ResourceUtils.getLocaleMessage("error.no.item.id"), id));
        }
        getService().deleteById(id);
        return item;
    }

    @Override
    public void deleteAllItems() {
        LOGGER.info("Deleting all items");
        getService().deleteAll();
    }

    protected DTOConverter getDTOConverter() {
        return this.dtoConverter;
    }

    @Override
    public S getService() {
        return this.service;
    }

    public void setService(final S service) {
        this.service = service;
    }
}
