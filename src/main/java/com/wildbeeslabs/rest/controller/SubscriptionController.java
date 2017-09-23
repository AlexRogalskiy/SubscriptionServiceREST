package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.api.rest.common.controller.ABaseController;
import com.wildbeeslabs.api.rest.common.exception.EmptyContentException;

import com.wildbeeslabs.rest.controller.proxy.SubscriptionProxyController;
import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.SubscriptionStatusInfo;
import com.wildbeeslabs.rest.model.dto.SubscriptionDTO;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
public class SubscriptionController<T extends Subscription, E extends SubscriptionDTO> extends ABaseController<T, E, Long, SubscriptionProxyController<T, E>> {

    /**
     * Get list of subscription entities (by subscription type)
     *
     * @param subStatus - subscription status
     * @return list of subscription entities
     */
    @RequestMapping(value = "/subscriptions", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getAll(@RequestParam(name = "substatus", required = false) SubscriptionStatusInfo.SubscriptionStatusType subStatus) {
        try {
            return new ResponseEntity<>(getProxyController().findAll(subStatus), HttpStatus.OK);
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
}
