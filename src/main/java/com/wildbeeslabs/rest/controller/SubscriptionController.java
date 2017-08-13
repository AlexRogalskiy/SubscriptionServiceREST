package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.service.interfaces.SubscriptionService;
import com.wildbeeslabs.rest.exception.ServiceException;
import com.wildbeeslabs.rest.model.Subscription;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * Subscription REST Application Controller
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@RestController
@RequestMapping("/api")
public class SubscriptionController {

    /**
     * Default logger instance
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

    @Autowired
    private SubscriptionService<Subscription> subscriptionService;

    /**
     * Get list of subscription entities
     *
     * @return list of subscriptions entities
     */
    @RequestMapping(value = "/subscription/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getAllSubscriptions() {
        LOGGER.info("Fetching all subscriptions");
        List<Subscription> subscriptions = subscriptionService.findAll();
        if (subscriptions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    /**
     * Get list of subscription entities by user ID
     *
     * @param userId - user identifier
     * @return list of subscriptions entities
     */
    @RequestMapping(value = "/user/{userId}/subscription", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getSubscriptionsByUserId(@PathVariable("userId") long userId) {
        LOGGER.info("Fetching subscriptions by user id {}", userId);
        List<Subscription> subscriptions = subscriptionService.findByUserId(userId);
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
    @RequestMapping(value = "/subscription/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getSubscriptionById(@PathVariable("id") long id) {
        LOGGER.info("Fetching subscription by id {}", id);
        Subscription subscription = subscriptionService.findById(id);
        if (Objects.isNull(subscription)) {
            String errorMessage = String.format("ERROR: subscription with id={%d} is not found", id);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    /**
     * Create new subscription entity
     *
     * @param subscription - subscription data
     * @param ucBuilder
     * @return request status code
     */
    @RequestMapping(value = "/subscription/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> createSubscription(@RequestBody Subscription subscription, UriComponentsBuilder ucBuilder) {
        LOGGER.info("Creating subscription : {}", subscription);
        if (subscriptionService.isExist(subscription)) {
            String errorMessage = String.format("ERROR: subscription with bane={%s} already exist", subscription.getName());
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.CONFLICT);
        }
        subscriptionService.save(subscription);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/subscription/{id}").buildAndExpand(subscription.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Update subscription entity
     *
     * @param id - subscription identifier
     * @param subscription - subscription entity
     * @return updated subscription entity
     */
    @RequestMapping(value = "/subscription/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody Subscription subscription) {
        LOGGER.info("Updating subscription by id {}", id);
        Subscription currentSubscription = subscriptionService.findById(id);
        if (Objects.isNull(currentSubscription)) {
            String errorMessage = String.format("ERROR: subscription with id={%d} is not found", id);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        currentSubscription.setName(subscription.getName());
        subscriptionService.update(currentSubscription);
        return new ResponseEntity<>(currentSubscription, HttpStatus.OK);
    }

    /**
     * Delete subscription entity
     *
     * @param id - subscription identifier
     * @return response status code
     */
    @RequestMapping(value = "/subscription/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        LOGGER.info("Deleting subscription by id {}", id);
        Subscription subscription = subscriptionService.findById(id);
        if (Objects.isNull(subscription)) {
            String errorMessage = String.format("ERROR: subscription with id={%d} is not found", id);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        subscriptionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete all subscription entities
     *
     * @return response status code
     */
    @RequestMapping(value = "/subscription/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public ResponseEntity<?> deleteAllUsers() {
        LOGGER.info("Deleting all subscriptions");
        subscriptionService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
