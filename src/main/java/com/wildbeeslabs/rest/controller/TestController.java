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
package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.controller.proxy.SubscriptionProxyController;
import com.wildbeeslabs.rest.exception.EmptyContentException;
import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.dto.converter.DTOConverter;
import com.wildbeeslabs.rest.model.dto.SubscriptionDTO;
import com.wildbeeslabs.rest.model.dto.SubscriptionDTOListWrapper;
import com.wildbeeslabs.rest.service.interfaces.ISubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 *
 * Subscription REST Controller implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 * @param <E>
 */
//@RestController
//@RequestMapping("/api")
public class TestController<T extends Subscription, E extends SubscriptionDTO> implements IBaseController<T, E> {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    private DTOConverter dtoConverter;
    @Autowired
    private SubscriptionProxyController<T, E, ISubscriptionService<T>> subscriptionProxyController;

    /**
     * Get list of subscription entities
     *
     * @return list of subscription entities
     */
    //@RequestMapping(value = "/subscriptions", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> getAll() {
        try {
            List<? extends T> items = subscriptionProxyController.getAllEntityItems();
            return new ResponseEntity<>(dtoConverter.convertToDTOAndWrap(items, SubscriptionDTO.class, SubscriptionDTOListWrapper.class), HttpStatus.OK);
        } catch (EmptyContentException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get subscription entity by ID
     *
     * @param id - subscription identifier
     * @return subscription entity
     */
    //@RequestMapping(value = "/subscription/{id:[\\d]+}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        T item = subscriptionProxyController.getEntityItemById(id);
        return new ResponseEntity<>(dtoConverter.convertToDTO(item, SubscriptionDTO.class), HttpStatus.OK);
    }

    /**
     * Create new subscription entity
     *
     * @param subscriptionDto - subscription data transfer object
     * @return response status code
     */
    //@RequestMapping(value = "/subscription", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> create(@RequestBody @Valid E subscriptionDto) {
        T item = subscriptionProxyController.createItem(subscriptionDto, (Class<? extends T>) Subscription.class);
        UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(request.getRequestURI() + "/{id}").buildAndExpand(item.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Update subscription entity by ID
     *
     * @param id - subscription identifier
     * @param subscriptionDto - subscription data transfer object
     * @return updated subscription entity
     */
    //@RequestMapping(value = "/subscription/{id:[\\d]+}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody E subscriptionDto) {
        T item = subscriptionProxyController.updateItem(id, subscriptionDto, (Class<? extends T>) Subscription.class);
        return new ResponseEntity<>(dtoConverter.convertToDTO(item, SubscriptionDTO.class), HttpStatus.OK);
    }

    /**
     * Delete subscription entity by ID
     *
     * @param id - subscription identifier
     * @return response status code
     */
    //@RequestMapping(value = "/subscription/{id:[\\d]+}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        T item = subscriptionProxyController.deleteEntityItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete all subscription entities
     *
     * @return response status code
     */
    //@RequestMapping(value = "/subscriptions", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    //@ResponseBody
    @Override
    public ResponseEntity<?> deleteAll() {
        subscriptionProxyController.deleteAllItems();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
