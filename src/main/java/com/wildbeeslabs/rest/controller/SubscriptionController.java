package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.exception.ServiceException;
import com.wildbeeslabs.rest.service.interfaces.SubscriptionService;
import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;
import com.wildbeeslabs.rest.service.interfaces.UserService;
import com.wildbeeslabs.rest.service.interfaces.UserSubOrderService;
import java.util.Date;

import java.util.List;
import java.util.Objects;

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
    @Autowired
    private UserService<User> userService;
    @Autowired
    private UserSubOrderService<UserSubOrder> userSubOrderService;

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
        User item = userService.findById(userId);
        if (Objects.isNull(item)) {
            String errorMessage = String.format("ERROR: item with id={%d} is not found", userId);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        List<T> subscriptions = getDefaultService().findByUserId(userId);
        if (subscriptions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    /**
     * Create new subscription entity by user ID
     *
     * @param userId - user identifier
     * @param subscription - subscription entity
     * @param ucBuilder
     * @return list of subscription entities
     */
    @RequestMapping(value = "/user/{userId}/subscription", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> createSubscriptionByUserId(@PathVariable("userId") Long userId, @RequestBody T subscription, UriComponentsBuilder ucBuilder) {
        LOGGER.info("Creating subscriptions by user id {}", userId);
        if (Objects.isNull(subscription) || Objects.isNull(subscription.getId())) {
            String errorMessage = String.format("ERROR: no subscription not found");
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.CONFLICT);
        }
        User userItem = userService.findById(userId);
        if (Objects.isNull(userItem)) {
            String errorMessage = String.format("ERROR: user item with id={%d} is not found", userId);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        T subscriptionItem = getDefaultService().findById(subscription.getId());
        if (Objects.isNull(subscriptionItem)) {
            String errorMessage = String.format("ERROR: subscription item with id={%d} is not found", subscription.getId());
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }

        UserSubOrder order = new UserSubOrder();
        order.setExpireAt(null);
        order.setSubscribedAt(new Date());
        order.setSubscription(subscriptionItem);
        order.setUser(userItem);

        if (userSubOrderService.isExist(order)) {
            String errorMessage = String.format("ERROR: order already exist");
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.CONFLICT);
        }
        userItem.getSubOrders().add(order);

        userService.save(userItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update subscription entities by user ID
     *
     * @param userId - user identifier
     * @return list of subscription entities
     */
    /*@RequestMapping(value = "/user/{userId}/subscription", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> updateSubscriptionsByUserId(@PathVariable("userId") Long userId) {
        LOGGER.info("Updating subscriptions by user id {}", userId);
        List<T> subscriptions = getService().findByUserId(userId);
        if (subscriptions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }*/
    /**
     * Delete subscription entities by user ID
     *
     * @param userId - user identifier
     * @return list of subscription entities
     */
    /*@RequestMapping(value = "/user/{userId}/subscription", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> deleteSubscriptionsByUserId(@PathVariable("userId") Long userId) {
        LOGGER.info("Deleting subscriptions by user id {}", userId);
        List<T> subscriptions = getService().findByUserId(userId);
        if (subscriptions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }*/
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
     * Update subscription entity by ID
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
     * Delete subscription entity by ID
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
     * Get default subscription service instance
     *
     * @return subscription service instance
     */
    @Override
    protected SubscriptionService<T> getDefaultService() {
        return subscriptionService;
    }
}
