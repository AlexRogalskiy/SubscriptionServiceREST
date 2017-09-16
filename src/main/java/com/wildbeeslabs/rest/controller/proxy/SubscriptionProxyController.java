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

import com.wildbeeslabs.rest.exception.EmptyContentException;
import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.SubscriptionStatusInfo;
import com.wildbeeslabs.rest.model.dto.wrapper.IBaseDTOListWrapper;
import com.wildbeeslabs.rest.model.dto.SubscriptionDTO;
import com.wildbeeslabs.rest.model.dto.wrapper.SubscriptionDTOListWrapper;
import com.wildbeeslabs.rest.service.interfaces.ISubscriptionService;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

/**
 *
 * Subscription Proxy Controller implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 * @param <E>
 */
@Component
public class SubscriptionProxyController<T extends Subscription, E extends SubscriptionDTO> extends ABaseProxyController<T, E, ISubscriptionService<T>> {

    public IBaseDTOListWrapper<? extends E> findByUserId(final Long userId) throws EmptyContentException {
        LOGGER.info("Fetching all subscriptions by user id {}", userId);
        List<? extends T> items = getService().findByUserId(userId);
        if (items.isEmpty()) {
            //throw new EmptyContentException(String.format(getLocaleMessage("error.no.content")));
            throw new EmptyContentException(getResource().formatMessage("error.no.content"));
        }
        return getDTOConverter().convertToDTOAndWrap(items, getDtoClass(), getDtoListClass());
    }

    public IBaseDTOListWrapper<? extends E> findAll(final SubscriptionStatusInfo.SubscriptionStatusType subStatus) throws EmptyContentException {
        LOGGER.info("Fetching all subscriptions by status {}", subStatus);
        List<? extends T> items = Objects.isNull(subStatus) ? getService().findAll() : getService().findByStatus(subStatus);
        if (items.isEmpty()) {
            //throw new EmptyContentException(String.format(getLocaleMessage("error.no.content")));
            throw new EmptyContentException(getResource().formatMessage("error.no.content"));
        }
        return getDTOConverter().convertToDTOAndWrap(items, getDtoClass(), getDtoListClass());
    }

    /**
     * Get default entity class
     *
     * @return entity class instance
     */
    @Override
    protected Class<? extends T> getEntityClass() {
        return (Class<? extends T>) Subscription.class;
    }

    /**
     * Get default DTO class
     *
     * @return entity class instance
     */
    @Override
    protected Class<? extends E> getDtoClass() {
        return (Class<? extends E>) SubscriptionDTO.class;
    }

    /**
     * Get default DTO Wrapper List class
     *
     * @return entity class instance
     */
    @Override
    protected Class<? extends IBaseDTOListWrapper> getDtoListClass() {
        return SubscriptionDTOListWrapper.class;
    }
}
