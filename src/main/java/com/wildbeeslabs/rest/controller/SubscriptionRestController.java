package com.wildbeeslabs.rest.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.websystique.springboot.model.User;
import com.websystique.springboot.service.UserService;
import com.websystique.springboot.util.CustomErrorType;

/**
 *
 * Subscription REST Application Controller
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionRestController {

    /**
     * Default logger instance
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionRestController.class);

    @Autowired
    SubscriptionService subscriptionService;

    /**
     * Get list of subscriptions
     *
     * @return list of subscribers
     */
    @RequestMapping(value = "/subscription/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllSubscriptions() {
        LOGGER.info("Fetching all subscriptions");
        List<Subscription> subscriptions = subscriptionService.findAllSubscriptions();
        if (subscriptions.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Subscription>>(subscriptions, HttpStatus.OK);
    }

    /**
     * Get subscriptions by user ID
     *
     * @param userId
     * @return list of subscriptions by user ID
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSubscriptionsByUserId(@PathVariable("id") long userId) {
        LOGGER.info("Fetching subscriptions by user id {}", userId);
        List<Subscription> subscriptions = subscriptionService.findByUserId(userId);
        if (subscriptions.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<User>(subscriptions, HttpStatus.OK);
    }

    /**
     * Create new subscription
     *
     * @param user
     * @param ucBuilder
     * @return request status code
     */
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<?> createSubscription(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);

        if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name "
                    + user.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    // ------------------- Update a User ------------------------------------------------
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);

        User currentUser = userService.findById(id);

        if (currentUser == null) {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());

        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    // ------------------- Delete a User-----------------------------------------
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting User with id {}", id);

        User user = userService.findById(id);
        if (user == null) {
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Delete All Users-----------------------------
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        logger.info("Deleting All Users");

        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

}
