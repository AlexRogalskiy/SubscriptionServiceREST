package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.service.interfaces.UserService;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.service.interfaces.BaseService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * User REST Controller implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
@RestController
@RequestMapping("/api")
public class UserController<T extends User> extends AbscractBaseController<T> {

    @Autowired
    private UserService<T> userService;

    /**
     * Get list of user entities
     *
     * @return list of user entities
     */
    @RequestMapping(value = "/users/", method = RequestMethod.GET, consumes = {"application/xml", "application/json"})
    @ResponseBody
    public ResponseEntity<?> getAllUsers() {
        return super.getAll();
    }

    /**
     * Get list of user entities by subscription type and date
     *
     * @param subType - subscription type
     * @param subDate - subscription date
     * @param subDateOrder - date order before / after date
     * @return list of user entities
     */
    @RequestMapping(params = {"type", "date", "order"}, value = "/user/", method = RequestMethod.GET, consumes = {"application/xml", "application/json"})
    @ResponseBody
    public ResponseEntity<?> getAllUsersBySubscriptionTypeAndDate(@PathVariable("date") Date subDate, @PathVariable("type") Subscription.SubscriptionType subType, @PathVariable("order") boolean subDateOrder) {
        LOGGER.info("Fetching all users by subscription date {} and type {} by date order {} (1 - before, 0 - after)", subType, subDate, subDateOrder);
        UserService.DateTypeOrder dateTypeOrder = subDateOrder ? UserService.DateTypeOrder.AFTER : UserService.DateTypeOrder.BEFORE;
        List<T> userList = userService.findBySubscriptionTypeAndDate(subDate, subType, dateTypeOrder);
        if (userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    /**
     * Get user entity by ID
     *
     * @param id - user identifier
     * @return user entity
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, consumes = {"application/xml", "application/json"})
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        return super.getById(id);
    }

    /**
     * Create new user entity
     *
     * @param user - user data
     * @param ucBuilder
     * @return response status code
     */
    @RequestMapping(value = "/user/", method = RequestMethod.POST, consumes = {"application/xml", "application/json"})
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody T user, UriComponentsBuilder ucBuilder) {
        ResponseEntity<?> response = super.create(user);
        response.getHeaders().setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return response;
    }

    /**
     * Update user entity
     *
     * @param id - user identifier
     * @param user - user entity
     * @return updated user entity
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, consumes = {"application/xml", "application/json"})
    @ResponseBody
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody T user) {
        return super.update(id, user);
    }

    /**
     * Delete user entity
     *
     * @param id - user identifier
     * @return response status code
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, consumes = {"application/xml", "application/json"})
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return super.delete(id);
    }

    /**
     * Delete all user entities
     *
     * @return response status code
     */
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE, consumes = {"application/xml", "application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public ResponseEntity<?> deleteAllUsers() {
        return super.deleteAll();
    }

    @Override
    protected BaseService<T> getService() {
        return userService;
    }
}
