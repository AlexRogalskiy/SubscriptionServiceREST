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

import com.wildbeeslabs.api.rest.common.controller.proxy.ABaseProxyController;
import com.wildbeeslabs.api.rest.common.exception.EmptyContentException;
import com.wildbeeslabs.api.rest.common.exception.ResourceNotFoundException;
import com.wildbeeslabs.api.rest.common.model.dto.wrapper.IBaseDTOListWrapper;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;
import com.wildbeeslabs.rest.model.UserSubOrderId;
import com.wildbeeslabs.rest.model.dto.UserSubOrderDTO;
import com.wildbeeslabs.rest.model.dto.wrapper.UserSubOrderDTOListWrapper;
import com.wildbeeslabs.rest.service.interfaces.IUserSubOrderService;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

/**
 *
 * UserSubscription Proxy Controller implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 * @param <E>
 */
@Component
public class UserSubscriptionProxyController<T extends UserSubOrder, E extends UserSubOrderDTO> extends ABaseProxyController<T, E, UserSubOrderId, IUserSubOrderService<T>> {

    public E findByUserAndSubscription(final User userItem, final Subscription subscriptionItem) {
        T item = this.findAllEntityByUserAndSubscription(userItem, subscriptionItem);
        return getDTOConverter().convertToDTO(item, getDtoClass());
    }

    public T findAllEntityByUserAndSubscription(final User userItem, final Subscription subscriptionItem) {
        LOGGER.info("Fetching subscription order by user id {} and subscription id {}", userItem.getId(), subscriptionItem.getId());
        T currentOrder = getService().findByUserAndSubscription(userItem, subscriptionItem);
        if (Objects.isNull(currentOrder)) {
            throw new ResourceNotFoundException(getResource().formatMessage("error.no.order.item.user.subscription.id", userItem.getId(), subscriptionItem.getId()));
        }
        return currentOrder;
    }

    public List<? extends T> findAllEntityByUser(final User userItem) throws EmptyContentException {
        LOGGER.info("Fetching subscription orders by user id {}", userItem.getId());
        List<? extends T> items = getService().findByUser(userItem);
        if (items.isEmpty()) {
            throw new EmptyContentException(getResource().formatMessage("error.no.content"));
        }
        return items;
    }

    public IBaseDTOListWrapper<? extends E> findByUser(final User userItem) throws EmptyContentException {
        List<? extends T> items = this.findAllEntityByUser(userItem);
        return getDTOConverter().convertToDTOAndWrap(items, getDtoClass(), getDtoListClass());
    }

    public List<? extends T> findAllEntityBySubscription(final Subscription subscriptionItem) throws EmptyContentException {
        LOGGER.info("Fetching subscription orders by subscription id {}", subscriptionItem.getId());
        List<? extends T> items = getService().findBySubscription(subscriptionItem);
        if (items.isEmpty()) {
            throw new EmptyContentException(getResource().formatMessage("error.no.content"));
        }
        return items;
    }

    public IBaseDTOListWrapper<? extends E> findBySubscription(final Subscription subscriptionItem) throws EmptyContentException {
        List<? extends T> items = this.findAllEntityBySubscription(subscriptionItem);
        return getDTOConverter().convertToDTOAndWrap(items, getDtoClass(), getDtoListClass());
    }

    public E updateEntityItem(final T toItemEntity, final E fromItemDto) {
        LOGGER.info("Updating item by id {}", toItemEntity.getId());
        T fromItemEntity = getDTOConverter().convertToEntity(fromItemDto, getEntityClass());
        getService().merge(toItemEntity, fromItemEntity);
        return getDTOConverter().convertToDTO(toItemEntity, getDtoClass());
    }

    public E deleteEntityItem(final T itemEntity) {
        LOGGER.info("Deleting item by id {}", itemEntity.getId());
        getService().delete(itemEntity);
        return getDTOConverter().convertToDTO(itemEntity, getDtoClass());
    }

    public void deleteEntityItems(final List<? extends T> itemEntityList) {
        LOGGER.info("Deleting items");
        getService().delete(itemEntityList);
    }

    /**
     * Get default entity class instance
     *
     * @return entity class instance
     */
    @Override
    protected Class<? extends T> getEntityClass() {
        return (Class<? extends T>) UserSubOrder.class;
    }

    /**
     * Get default DTO class instance
     *
     * @return entity class instance
     */
    @Override
    protected Class<? extends E> getDtoClass() {
        return (Class<? extends E>) UserSubOrderDTO.class;
    }

    /**
     * Get default DTO Wrapper List class
     *
     * @return entity class instance
     */
    @Override
    protected Class<? extends IBaseDTOListWrapper> getDtoListClass() {
        return UserSubOrderDTOListWrapper.class;
    }
}
