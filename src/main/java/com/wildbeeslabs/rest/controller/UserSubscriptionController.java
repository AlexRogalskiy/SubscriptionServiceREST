package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.exception.BadRequestException;
import com.wildbeeslabs.rest.exception.ResourceAlreadyExistException;
import com.wildbeeslabs.rest.exception.ResourceNotFoundException;
import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;
import com.wildbeeslabs.rest.model.dto.SubscriptionDTO;
import com.wildbeeslabs.rest.model.dto.UserDTO;
import com.wildbeeslabs.rest.service.interfaces.SubscriptionService;
import com.wildbeeslabs.rest.service.interfaces.UserService;
import com.wildbeeslabs.rest.service.interfaces.UserSubOrderService;
import com.wildbeeslabs.rest.model.dto.UserSubOrderDTO;

import java.util.List;
import java.util.Objects;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
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
//@Validated
@RequestMapping("/api")
public class UserSubscriptionController<T extends UserSubOrder, E extends UserSubOrderDTO> extends ABaseController<T, E> {

    @Autowired
    private SubscriptionService<Subscription> subscriptionService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private UserSubOrderService<T> userSubOrderService;

    /**
     * Get list of subscription entities by user ID
     *
     * @param userId - user identifier
     * @return list of subscription entities
     */
    @RequestMapping(value = "/user/{userId}/subscriptions", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getSubscriptionsByUserId(@PathVariable("userId") Long userId) {
        LOGGER.info("Fetching subscriptions by user id {}", userId);
        User userItem = userService.findById(userId);
        if (Objects.isNull(userItem)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.user.item.id"), userId));
        }
        /*List<T> subscriptionOrders = getDefaultService().findByUser(userItem);
        if (subscriptionOrders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(subscriptionOrders, HttpStatus.OK);*/
        List<Subscription> subscriptions = subscriptionService.findByUserId(userId);
        if (subscriptions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //return new ResponseEntity<>(subscriptions.stream().map(item -> convertToDTO(item, SubscriptionDTO.class)).collect(Collectors.toList()), HttpStatus.OK);
        return new ResponseEntity<>(convertToDTOList(subscriptions, SubscriptionDTO.class), HttpStatus.OK);
    }

    /**
     * Get subscription order entity by user ID / subscription ID
     *
     * @param userId - user identifier
     * @param subscriptionId - subscription identifier
     * @return subscription order entity
     */
    @RequestMapping(value = "/user/{userId}/subscription/{subscriptionId}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getSubscriptionsByUserId(@PathVariable("userId") Long userId, @PathVariable("subscriptionId") Long subscriptionId) {
        LOGGER.info("Fetching subscription order by subscription id {} and user id {}", subscriptionId, userId);
        User userItem = userService.findById(userId);
        if (Objects.isNull(userItem)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.user.item.id"), userId));
        }
        Subscription subscriptionItem = subscriptionService.findById(subscriptionId);
        if (Objects.isNull(subscriptionItem)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.subscription.item.id"), subscriptionId));
        }
        T currentOrder = getDefaultService().findByUserAndSubscription(userItem, subscriptionItem);
        if (Objects.isNull(currentOrder)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.order.item.user.subscription.id"), userItem.getId(), subscriptionItem.getId()));
        }
        return new ResponseEntity<>(convertToDTO(currentOrder, getDtoClass()), HttpStatus.OK);
    }

    /**
     * Create new subscription order entity by user ID
     *
     * @param userId - user identifier
     * @param orderDto - subscription order data transfer object
     * @param ucBuilder - URI component builder
     * @return response status code
     */
    @RequestMapping(value = "/user/{userId}/subscription", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> createSubscriptionByUserId(@PathVariable("userId") Long userId, @RequestBody @Valid E orderDto, UriComponentsBuilder ucBuilder) {
        LOGGER.info("Creating subscription order by user id {}", userId);
        T orderEntity = convertToEntity(orderDto, getEntityClass());
        if (Objects.isNull(orderEntity)) {
            throw new BadRequestException(String.format(getLocaleMessage("error.no.order.item")));
        }
        if (Objects.isNull(orderEntity.getSubscription())) {
            throw new BadRequestException(String.format(getLocaleMessage("error.no.subscription.item")));
        }
        User userItem = userService.findById(userId);
        if (Objects.isNull(userItem)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.user.item.id"), userId));
        }
        Subscription subscriptionItem = subscriptionService.findById(orderEntity.getSubscription().getId());
        if (Objects.isNull(subscriptionItem)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.subscription.item.id"), orderEntity.getSubscription().getId()));
        }
        orderEntity.setSubscription(subscriptionItem);
        orderEntity.setUser(userItem);

        if (getDefaultService().isExist(orderEntity)) {
            throw new ResourceAlreadyExistException(String.format(getLocaleMessage("error.already.exist.order.item"), orderEntity.getPk()));
        }
        //userItem.getSubOrders().add(order);
        //userService.save(userItem);
        getDefaultService().save(orderEntity);
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
    @RequestMapping(value = "/user/{userId}/subscription/{subscriptionId}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> updateSubscriptionsByUserIdAndSubscriptionId(@PathVariable("userId") Long userId, @PathVariable("subscriptionId") Long subscriptionId, @RequestBody @Valid E orderDto) {
        LOGGER.info("Updating subscription order by subscription id {} and user id {}", subscriptionId, userId);
        T orderEntity = convertToEntity(orderDto, getEntityClass());
        if (Objects.isNull(orderEntity)) {
            throw new BadRequestException(String.format(getLocaleMessage("error.no.order.item")));
        }
        User userItem = userService.findById(userId);
        if (Objects.isNull(userItem)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.user.item.id"), userId));
        }
        Subscription subscriptionItem = subscriptionService.findById(subscriptionId);
        if (Objects.isNull(subscriptionItem)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.subscription.item.id"), subscriptionId));
        }
        //order.setSubscription(subscriptionItem);
        //order.setUser(userItem);
        T currentOrder = getDefaultService().findByUserAndSubscription(userItem, subscriptionItem);//.findById(order.getPk());
        if (Objects.isNull(currentOrder)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.order.item.user.subscription.id"), userItem.getId(), subscriptionItem.getId()));
        }
        getDefaultService().merge(currentOrder, orderEntity);
        return new ResponseEntity<>(convertToDTO(currentOrder, getDtoClass()), HttpStatus.OK);
    }

    /**
     * Delete subscription order entity by user ID / subscription ID
     *
     * @param userId - user identifier
     * @param subscriptionId - subscription identifier
     * @return response status code
     */
    @RequestMapping(value = "/user/{userId}/subscription/{subscriptionId}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> deleteSubscriptionsByUserId(@PathVariable("userId") Long userId, @PathVariable("subscriptionId") Long subscriptionId) {
        LOGGER.info("Deleting subscription order by subscription id {} and user id {}", subscriptionId, userId);
        User userItem = userService.findById(userId);
        if (Objects.isNull(userItem)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.user.item.id"), userId));
        }
        Subscription subscriptionItem = subscriptionService.findById(subscriptionId);
        if (Objects.isNull(subscriptionItem)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.subscription.item.id"), subscriptionId));
        }
        T currentOrder = getDefaultService().findByUserAndSubscription(userItem, subscriptionItem);
        if (Objects.isNull(currentOrder)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.order.item.user.subscription.id"), userItem.getId(), subscriptionItem.getId()));
        }
        getDefaultService().delete(currentOrder);
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
        LOGGER.info("Deleting all subscription orders by user id {}", userId);
        User userItem = userService.findById(userId);
        if (Objects.isNull(userItem)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.user.item.id"), userId));
        }
        List<T> subscriptionOrders = getDefaultService().findByUser(userItem);
        if (subscriptionOrders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        getDefaultService().delete(subscriptionOrders);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get list of user entities by subscription ID
     *
     * @param subscriptionId - subscription identifier
     * @return list of user entities
     */
    @RequestMapping(value = "/subscription/{subscriptionId}/users", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getUsersBySubscriptionId(@PathVariable("subscriptionId") Long subscriptionId) {
        LOGGER.info("Fetching users by subscription id {}", subscriptionId);
        Subscription subscriptionItem = subscriptionService.findById(subscriptionId);
        if (Objects.isNull(subscriptionItem)) {
            throw new ResourceNotFoundException(String.format(getLocaleMessage("error.no.subscription.item.id"), subscriptionId));
        }
        /*List<T> userOrders = getDefaultService().findBySubscription(subscriptionItem);
        if (userOrders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userOrders, HttpStatus.OK);*/
        List<User> users = userService.findBySubscriptionId(subscriptionId);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //return new ResponseEntity<>(users.stream().map(item -> convertToDTO(item, UserDTO.class)).collect(Collectors.toList()), HttpStatus.OK);
        return new ResponseEntity<>(convertToDTOList(users, UserDTO.class), HttpStatus.OK);
    }

    /**
     * Get default subscription order service instance
     *
     * @return subscription service instance
     */
    @Override
    protected UserSubOrderService<T> getDefaultService() {
        return userSubOrderService;
    }

    /**
     * Get default entity class instance
     *
     * @return entity class instance
     */
    @Override
    protected Class<T> getEntityClass() {
        return (Class<T>) UserSubOrder.class;
    }

    /**
     * Get default DTO class instance
     *
     * @return entity class instance
     */
    @Override
    protected Class<E> getDtoClass() {
        return (Class<E>) UserSubOrderDTO.class;
    }
}
