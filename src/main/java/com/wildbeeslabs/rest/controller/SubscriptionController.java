package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.exception.ServiceException;
import com.wildbeeslabs.rest.service.interfaces.SubscriptionService;
import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;
import com.wildbeeslabs.rest.service.interfaces.UserService;
import com.wildbeeslabs.rest.service.interfaces.UserSubOrderService;

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
    @RequestMapping(value = "/user/{userId}/subscriptions", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getSubscriptionsByUserId(@PathVariable("userId") Long userId) {
        LOGGER.info("Fetching subscriptions by user id {}", userId);
        User userItem = userService.findById(userId);
        if (Objects.isNull(userItem)) {
            String errorMessage = String.format("ERROR: user item with id={%d} is not found", userId);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        /*List<UserSubOrder> subscriptionOrders = userSubOrderService.findByUser(userItem);
        if (subscriptionOrders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(subscriptionOrders, HttpStatus.OK);*/
        List<T> subscriptions = getDefaultService().findByUserId(userId);
        if (subscriptions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    /**
     * Get subscription order entity by user ID / subscription ID
     *
     * @param userId - user identifier
     * @param subscriptionId - subscription identifier
     * @return subscription order entity
     */
    @RequestMapping(value = "/user/{userId}/subscription/{subscriptionId}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getSubscriptionsByUserId(@PathVariable("userId") Long userId, @PathVariable("subscriptionId") Long subscriptionId) {
        LOGGER.info("Fetching subscription id {} by user id {}", subscriptionId, userId);
        User userItem = userService.findById(userId);
        if (Objects.isNull(userItem)) {
            String errorMessage = String.format("ERROR: item with id={%d} is not found", userId);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        T subscriptionItem = getDefaultService().findById(subscriptionId);
        if (Objects.isNull(subscriptionItem)) {
            String errorMessage = String.format("ERROR: subscription item with id={%d} is not found", subscriptionId);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        UserSubOrder order = new UserSubOrder();
        order.setSubscription(subscriptionItem);
        order.setUser(userItem);

        UserSubOrder currentOrder = userSubOrderService.findById(order.getPk());
        if (Objects.isNull(currentOrder)) {
            String errorMessage = String.format("ERROR: subscription order with id={%s} is not found", order.getPk());
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentOrder, HttpStatus.OK);
    }

    /**
     * Create new subscription order entity by user ID
     *
     * @param userId - user identifier
     * @param order - subscription order entity
     * @param ucBuilder - URI builder
     * @return response status code
     */
    @RequestMapping(value = "/user/{userId}/subscription", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> createSubscriptionByUserId(@PathVariable("userId") Long userId, @RequestBody UserSubOrder order, UriComponentsBuilder ucBuilder) {
        LOGGER.info("Creating subscription by user id {}", userId);
        if (Objects.isNull(order) || Objects.isNull(order.getSubscription()) || Objects.isNull(order.getSubscription().getId())) {
            String errorMessage = String.format("ERROR: subscription entity is empty or not valid");
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.CONFLICT);
        }
        User userItem = userService.findById(userId);
        if (Objects.isNull(userItem)) {
            String errorMessage = String.format("ERROR: user item with id={%d} is not found", userId);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        T subscriptionItem = getDefaultService().findById(order.getSubscription().getId());
        if (Objects.isNull(subscriptionItem)) {
            String errorMessage = String.format("ERROR: subscription item with id={%d} is not found", order.getSubscription().getId());
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
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
     * Update subscription order entity by user ID / subscription ID
     *
     * @param userId - user identifier
     * @param subscriptionId - subscription identifier
     * @param order - subscription order entity
     * @return updated subscription order entity
     */
    @RequestMapping(value = "/user/{userId}/subscription/{subscriptionId}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> updateSubscriptionsByUserId(@PathVariable("userId") Long userId, @PathVariable("subscriptionId") Long subscriptionId, @RequestBody UserSubOrder order) {
        LOGGER.info("Updating subscription with {} by user id {}", subscriptionId, userId);
        if (Objects.isNull(order) || Objects.isNull(order.getSubscription())) {
            String errorMessage = String.format("ERROR: subscription entity is empty or not valid");
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.CONFLICT);
        }
        User userItem = userService.findById(userId);
        if (Objects.isNull(userItem)) {
            String errorMessage = String.format("ERROR: user item with id={%d} is not found", userId);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        T subscriptionItem = getDefaultService().findById(subscriptionId);
        if (Objects.isNull(subscriptionItem)) {
            String errorMessage = String.format("ERROR: subscription item with id={%d} is not found", subscriptionId);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        order.setSubscription(subscriptionItem);
        order.setUser(userItem);

        UserSubOrder currentOrder = userSubOrderService.findById(order.getPk());
        if (Objects.isNull(currentOrder)) {
            String errorMessage = String.format("ERROR: subscription order with id={%s} is not found", order.getPk());
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        userSubOrderService.merge(currentOrder, order);
        return new ResponseEntity<>(currentOrder, HttpStatus.OK);
    }

    /**
     * Delete subscription order entity by user ID / subscription ID
     *
     * @param userId - user identifier
     * @param subscriptionId - subscription identifier
     * @return response status code
     */
    @RequestMapping(value = "/user/{userId}/subscription/{subscriptionId}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> deleteSubscriptionsByUserId(@PathVariable("userId") Long userId, @PathVariable("subscriptionId") Long subscriptionId) {
        LOGGER.info("Deleting subscription with id {} by user id {}", subscriptionId, userId);
        User userItem = userService.findById(userId);
        if (Objects.isNull(userItem)) {
            String errorMessage = String.format("ERROR: user item with id={%d} is not found", userId);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        T subscriptionItem = getDefaultService().findById(subscriptionId);
        if (Objects.isNull(subscriptionItem)) {
            String errorMessage = String.format("ERROR: subscription item with id={%d} is not found", subscriptionId);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        UserSubOrder order = new UserSubOrder();
        order.setSubscription(subscriptionItem);
        order.setUser(userItem);

        UserSubOrder currentOrder = userSubOrderService.findById(order.getPk());
        if (Objects.isNull(currentOrder)) {
            String errorMessage = String.format("ERROR: subscription order with id={%s} is not found", order.getPk());
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        userSubOrderService.delete(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete all subscription order entities by user ID
     *
     * @param userId - user identifier
     * @return response status code
     */
    @RequestMapping(value = "/user/{userId}/subscriptions", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> deleteAllSubscriptions(@PathVariable("userId") Long userId) {
        LOGGER.info("Deleting all subscriptions by user id {}", userId);
        User userItem = userService.findById(userId);
        if (Objects.isNull(userItem)) {
            String errorMessage = String.format("ERROR: user item with id={%d} is not found", userId);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        List<UserSubOrder> subscriptionOrders = userSubOrderService.findByUser(userItem);
        if (subscriptionOrders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        userSubOrderService.delete(subscriptionOrders);
        return new ResponseEntity<>(HttpStatus.OK);
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
     * Get list of user entities by subscription ID
     *
     * @param subscriptionId - subscription identifier
     * @return list of user entities
     */
    @RequestMapping(value = "/subscription/{subscriptionId}/users", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getUsersBySubscriptionId(@PathVariable("subscriptionId") Long subscriptionId) {
        LOGGER.info("Fetching users by subscription id {}", subscriptionId);
        T subscriptionItem = getDefaultService().findById(subscriptionId);
        if (Objects.isNull(subscriptionItem)) {
            String errorMessage = String.format("ERROR: subscription item with id={%d} is not found", subscriptionId);
            LOGGER.error(errorMessage);
            return new ResponseEntity<>(new ServiceException(errorMessage), HttpStatus.NOT_FOUND);
        }
        /*List<UserSubOrder> userOrders = userSubOrderService.findBySubscription(subscriptionItem);
        if (userOrders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userOrders, HttpStatus.OK);*/
        List<User> users = userService.findBySubscriptionId(subscriptionId);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Create new subscription entity
     *
     * @param subscription - subscription data
     * @param ucBuilder - URI builder instance
     * @return response status code
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
