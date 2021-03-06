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
import com.wildbeeslabs.api.rest.common.model.dto.wrapper.IBaseDTOListWrapper;
import com.wildbeeslabs.api.rest.common.exception.EmptyContentException;

import com.wildbeeslabs.rest.model.SubscriptionStatusInfo;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.dto.UserDTO;
import com.wildbeeslabs.rest.model.dto.wrapper.UserDTOListWrapper;
import com.wildbeeslabs.rest.service.interfaces.IUserService;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

/**
 *
 * User Proxy Controller implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 * @param <E>
 */
@Component
public class UserProxyController<T extends User, E extends UserDTO> extends ABaseProxyController<T, E, Long, IUserService<T>> {

    public List<? extends T> findAllEntityBySubscriptionStatusAndDate(final Date subDate, final SubscriptionStatusInfo.SubscriptionStatusType subStatus, final Boolean subDateOrder) throws EmptyContentException {
        LOGGER.info("Fetching all users by subscription date {}, status {}, date order {} (1 - before, 0 - after)", subDate, subStatus, subDateOrder);
        IUserService.DateTypeOrder dateTypeOrder = Objects.equals(Boolean.TRUE, subDateOrder) ? IUserService.DateTypeOrder.AFTER : IUserService.DateTypeOrder.BEFORE;
        List<? extends T> items = getService().findAllBySubscriptionStatusAndDate(subDate, subStatus, dateTypeOrder);
        if (items.isEmpty()) {
            throw new EmptyContentException(getResource().formatMessage("error.no.content"));
        }
        return items;
    }

    public IBaseDTOListWrapper<? extends E> findAllBySubscriptionStatusAndDate(final Date subDate, final SubscriptionStatusInfo.SubscriptionStatusType subStatus, final Boolean subDateOrder) throws EmptyContentException {
        List<? extends T> items = this.findAllEntityBySubscriptionStatusAndDate(subDate, subStatus, subDateOrder);
        return getDTOConverter().convertToDTOAndWrap(items, getDtoClass(), getDtoListClass());
    }

    public List<? extends T> findAllEntityBySubscriptionId(final Long subscriptionId) throws EmptyContentException {
        LOGGER.info("Fetching all users by subscription id {}", subscriptionId);
        List<? extends T> items = getService().findBySubscriptionId(subscriptionId);
        if (items.isEmpty()) {
            throw new EmptyContentException(getResource().formatMessage("error.no.content"));
        }
        return items;
    }

    public IBaseDTOListWrapper<? extends E> findAllBySubscriptionId(final Long subscriptionId) throws EmptyContentException {
        List<? extends T> items = this.findAllEntityBySubscriptionId(subscriptionId);
        return getDTOConverter().convertToDTOAndWrap(items, getDtoClass(), getDtoListClass());
    }

    public List<? extends T> findAllEntityByStatus(final User.UserStatusType status) throws EmptyContentException {
        LOGGER.info("Fetching all users by status {}", status);
        List<? extends T> items = getService().findByStatus(status);
        if (items.isEmpty()) {
            throw new EmptyContentException(getResource().formatMessage("error.no.content"));
        }
        return items;
    }

    public List<? extends T> findAllEntity(final User.UserStatusType status, final Date subDate, final SubscriptionStatusInfo.SubscriptionStatusType subStatus, final Boolean subDateOrder) throws EmptyContentException {
        List<? extends T> items = this.findAllEntityBySubscriptionStatusAndDate(subDate, subStatus, subDateOrder);
        if (Objects.nonNull(status)) {
            items = items.stream().parallel().filter(item -> Objects.equals(status, item.getStatus())).collect(Collectors.toList());
        }
        if (items.isEmpty()) {
            throw new EmptyContentException(getResource().formatMessage("error.no.content"));
        }
        return items;
    }

    public IBaseDTOListWrapper<? extends E> findAll(final User.UserStatusType status, final Date subDate, final SubscriptionStatusInfo.SubscriptionStatusType subStatus, final Boolean subDateOrder) throws EmptyContentException {
        List<? extends T> items = this.findAllEntity(status, subDate, subStatus, subDateOrder);
        return getDTOConverter().convertToDTOAndWrap(items, getDtoClass(), getDtoListClass());
    }

    /**
     * Get default entity class instance
     *
     * @return entity class instance
     */
    @Override
    protected Class<? extends T> getEntityClass() {
        return (Class<? extends T>) User.class;
    }

    /**
     * Get default DTO class instance
     *
     * @return entity class instance
     */
    @Override
    protected Class<? extends E> getDtoClass() {
        return (Class<? extends E>) UserDTO.class;
    }

    /**
     * Get default DTO Wrapper List class
     *
     * @return entity class instance
     */
    @Override
    protected Class<? extends IBaseDTOListWrapper> getDtoListClass() {
        return UserDTOListWrapper.class;
    }
}
