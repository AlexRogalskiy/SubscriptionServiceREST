package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.controller.proxy.IBaseProxyController;
import com.wildbeeslabs.rest.controller.proxy.SubscriptionProxyController;
import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.dto.SubscriptionDTO;
import com.wildbeeslabs.rest.service.interfaces.IBaseService;
import com.wildbeeslabs.rest.service.interfaces.ISubscriptionService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

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
@RestController
@RequestMapping("/api")
public class SubscriptionController<T extends Subscription, E extends SubscriptionDTO> extends ABaseController<T, E> {

    @Autowired
    private SubscriptionProxyController<T, E, ISubscriptionService<T>> subscriptionProxyController;

    /**
     * Get list of subscription entities
     *
     * @return list of subscription entities
     */
    @RequestMapping(value = "/subscriptions", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> getAll() {
        return super.getAll();
    }

    /**
     * Get subscription entity by ID
     *
     * @param id - subscription identifier
     * @return subscription entity
     */
    @RequestMapping(value = "/subscription/{id:[\\d]+}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return super.getById(id);
    }

    /**
     * Create new subscription entity
     *
     * @param subscriptionDto - subscription data transfer object
     * @return response status code
     */
    @RequestMapping(value = "/subscription", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> create(@RequestBody @Valid E subscriptionDto) {
        /*
        UriComponentsBuilder bc = UriComponentsBuilder.newInstance();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(request.getRequestURI() + "/{id}").buildAndExpand(subscriptionDto.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
         */
        return super.create(subscriptionDto);
    }

    /**
     * Update subscription entity by ID
     *
     * @param id - subscription identifier
     * @param subscriptionDto - subscription data transfer object
     * @return updated subscription entity
     */
    @RequestMapping(value = "/subscription/{id:[\\d]+}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid E subscriptionDto) {
        return super.update(id, subscriptionDto);
    }

    /**
     * Delete subscription entity by ID
     *
     * @param id - subscription identifier
     * @return response status code
     */
    @RequestMapping(value = "/subscription/{id:[\\d]+}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return super.delete(id);
    }

    /**
     * Delete all subscription entities
     *
     * @return response status code
     */
    @RequestMapping(value = "/subscriptions", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    //@ResponseBody
    @Override
    public ResponseEntity<?> deleteAll() {
        return super.deleteAll();
    }

    /**
     * Get default Proxy Controller
     *
     * @return entity class instance
     */
    @Override
    protected IBaseProxyController<T, E, ? extends IBaseService<T>> getProxyController() {
        return this.subscriptionProxyController;
    }
}
