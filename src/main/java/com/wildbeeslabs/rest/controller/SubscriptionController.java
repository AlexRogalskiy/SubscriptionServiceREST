package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.service.interfaces.SubscriptionService;
import com.wildbeeslabs.rest.model.Subscription;

import java.util.List;

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
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * Subscription REST Controller implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
@RestController
@RequestMapping("/api")
public class SubscriptionController<T extends Subscription> extends AbscractBaseController<T> {

    @Autowired
    private SubscriptionService<T> subscriptionService;

    /**
     * Get list of subscription entities
     *
     * @return list of subscription entities
     */
    @RequestMapping(value = "/subscriptions", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getAllSubscriptions() {
        return super.getAll();
    }

    /**
     * Get list of subscription entities by user ID
     *
     * @param userId - user identifier
     * @return list of subscription entities
     */
    @RequestMapping(value = "/user/{userId}/subscription", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getSubscriptionsByUserId(@PathVariable("userId") Long userId) {
        LOGGER.info("Fetching subscriptions by user id {}", userId);
        List<T> subscriptions = getService().findByUserId(userId);
        if (subscriptions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    /**
     * Get subscription entity by ID
     *
     * @param id - subscription identifier
     * @return subscription entity
     */
    @RequestMapping(value = "/subscription/{id}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getSubscriptionById(@PathVariable("id") Long id) {
        return super.getById(id);
    }

    /**
     * Create new subscription entity
     *
     * @param subscription - subscription data
     * @param ucBuilder - URI builder instance
     * @return request status code
     */
    @RequestMapping(value = "/subscription", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> createSubscription(@RequestBody T subscription, UriComponentsBuilder ucBuilder) {
        return super.create(subscription);
    }

    /**
     * Update subscription entity
     *
     * @param id - subscription identifier
     * @param subscription - subscription entity
     * @return updated subscription entity
     */
    @RequestMapping(value = "/subscription/{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> updateSubscription(@PathVariable("id") Long id, @RequestBody T subscription) {
        return super.update(id, subscription);
    }

    /**
     * Delete subscription entity
     *
     * @param id - subscription identifier
     * @return response status code
     */
    @RequestMapping(value = "/subscription/{id}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> deleteSubscription(@PathVariable("id") Long id) {
        return super.delete(id);
    }

    /**
     * Delete all subscription entities
     *
     * @return response status code
     */
    @RequestMapping(value = "/subscriptions", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> deleteAllSubscriptions() {
        return super.deleteAll();
    }

    /**
     * Get subscription service instance
     *
     * @return subscription service instance
     */
    @Override
    protected SubscriptionService<T> getService() {
        return subscriptionService;
    }
}
