package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.controller.proxy.IBaseProxyController;
import com.wildbeeslabs.rest.controller.proxy.SubscriptionProxyController;
import com.wildbeeslabs.rest.controller.proxy.UserProxyController;
import com.wildbeeslabs.rest.controller.proxy.UserSubscriptionProxyController;
import com.wildbeeslabs.rest.exception.EmptyContentException;
import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;
import com.wildbeeslabs.rest.model.dto.SubscriptionDTO;
import com.wildbeeslabs.rest.model.dto.UserDTO;
import com.wildbeeslabs.rest.model.dto.UserSubOrderDTO;
import com.wildbeeslabs.rest.service.interfaces.IBaseService;
import com.wildbeeslabs.rest.service.interfaces.ISubscriptionService;
import com.wildbeeslabs.rest.service.interfaces.IUserService;
import com.wildbeeslabs.rest.service.interfaces.IUserSubOrderService;

import java.util.List;
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
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * User-Subscription REST Controller implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 * @param <E>
 */
@RestController
@RequestMapping("/api")
public class UserSubscriptionController<T extends UserSubOrder, E extends UserSubOrderDTO> extends ABaseController<T, E> {

    @Autowired
    private SubscriptionProxyController<Subscription, SubscriptionDTO, ISubscriptionService<Subscription>> subscriptionProxyController;
    @Autowired
    private UserProxyController<User, UserDTO, IUserService<User>> userProxyController;
    @Autowired
    private UserSubscriptionProxyController<T, E, IUserSubOrderService<T>> userSubscriptionProxyController;

    /**
     * Get list of subscription entities by user ID
     *
     * @param userId - user identifier
     * @return list of subscription entities
     */
    @RequestMapping(value = "/user/{userId:[\\d]+}/subscriptions", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getSubscriptionsByUserId(@PathVariable("userId") Long userId) {
        //User userItem = userProxyController.getEntityItemById(userId);
        /*List<T> subscriptionOrders = getDefaultService().findByUser(userItem);
        if (subscriptionOrders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(subscriptionOrders, HttpStatus.OK);*/
        try {
            return new ResponseEntity<>(subscriptionProxyController.findByUserId(userId), HttpStatus.OK);
        } catch (EmptyContentException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get subscription order entity by user ID / subscription ID
     *
     * @param userId - user identifier
     * @param subscriptionId - subscription identifier
     * @return subscription order entity
     */
    @RequestMapping(value = "/user/{userId:[\\d]+}/subscription/{subscriptionId:[\\d]+}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getSubscriptionByUserIdAndSubscriptionId(@PathVariable("userId") Long userId, @PathVariable("subscriptionId") Long subscriptionId) {
        User userItem = userProxyController.getEntityItemById(userId);
        Subscription subscriptionItem = subscriptionProxyController.getEntityItemById(subscriptionId);
        return new ResponseEntity<>(userSubscriptionProxyController.findByUserAndSubscription(userItem, subscriptionItem), HttpStatus.OK);
    }

    /**
     * Create new subscription order entity by user ID
     *
     * @param userId - user identifier
     * @param orderDto - subscription order data transfer object
     * @param ucBuilder - URI component builder
     * @return response status code
     */
    @RequestMapping(value = "/user/{userId:[\\d]+}/subscription", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> createSubscriptionByUserId(@PathVariable("userId") Long userId, @RequestBody @Valid E orderDto, UriComponentsBuilder ucBuilder) {
        UserDTO userItem = userProxyController.getItemById(userId);
        SubscriptionDTO subscriptionItem = subscriptionProxyController.getItemById(orderDto.getSubscription().getId());
        orderDto.setSubscription(subscriptionItem);
        orderDto.setUser(userItem);
        userSubscriptionProxyController.createItem(orderDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update subscription order entity by user ID / subscription ID
     *
     * @param userId - user identifier
     * @param subscriptionId - subscription identifier
     * @param orderDto - subscription order data transfer object
     * @return updated subscription order entity
     */
    @RequestMapping(value = "/user/{userId:[\\d]+}/subscription/{subscriptionId:[\\d]+}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> updateSubscriptionsByUserIdAndSubscriptionId(@PathVariable("userId") Long userId, @PathVariable("subscriptionId") Long subscriptionId, @RequestBody @Valid E orderDto) {
        User userItem = userProxyController.getEntityItemById(userId);
        Subscription subscriptionItem = subscriptionProxyController.getEntityItemById(orderDto.getSubscription().getId());
        T currentOrder = userSubscriptionProxyController.findAllEntityByUserAndSubscription(userItem, subscriptionItem);
        userSubscriptionProxyController.updateEntityItem(currentOrder, orderDto);
        return new ResponseEntity<>(currentOrder, HttpStatus.OK);
    }

    /**
     * Delete subscription order entity by user ID / subscription ID
     *
     * @param userId - user identifier
     * @param subscriptionId - subscription identifier
     * @return response status code
     */
    @RequestMapping(value = "/user/{userId:[\\d]+}/subscription/{subscriptionId:[\\d]+}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> deleteSubscriptionsByUserId(@PathVariable("userId") Long userId, @PathVariable("subscriptionId") Long subscriptionId) {
        User userItem = userProxyController.getEntityItemById(userId);
        Subscription subscriptionItem = subscriptionProxyController.getEntityItemById(subscriptionId);
        T currentOrder = userSubscriptionProxyController.findAllEntityByUserAndSubscription(userItem, subscriptionItem);
        userSubscriptionProxyController.deleteEntityItem(currentOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete all subscription order entities by user ID
     *
     * @param userId - user identifier
     * @return response status code
     */
    @RequestMapping(value = "/user/{userId:[\\d]+}/subscriptions", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> deleteAllSubscriptions(@PathVariable("userId") Long userId) {
        User userItem = userProxyController.getEntityItemById(userId);
        try {
            List<? extends T> subscriptionOrders = userSubscriptionProxyController.findAllEntityByUser(userItem);
            userSubscriptionProxyController.deleteEntityItems(subscriptionOrders);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyContentException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    /**
     * Get list of user entities by subscription ID
     *
     * @param subscriptionId - subscription identifier
     * @return list of user entities
     */
    @RequestMapping(value = "/subscription/{subscriptionId:[\\d]+}/users", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getUsersBySubscriptionId(@PathVariable("subscriptionId") Long subscriptionId) {
        //Subscription subscriptionItem = subscriptionProxyController.getEntityItemById(subscriptionId);
        /*List<T> userOrders = getDefaultService().findBySubscription(subscriptionItem);
        if (userOrders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userOrders, HttpStatus.OK);*/
        try {
            return new ResponseEntity<>(userProxyController.findAllEntityBySubscriptionId(subscriptionId), HttpStatus.OK);
        } catch (EmptyContentException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    protected IBaseProxyController<T, E, ? extends IBaseService<T>> getProxyController() {
        return this.userSubscriptionProxyController;
    }
}
