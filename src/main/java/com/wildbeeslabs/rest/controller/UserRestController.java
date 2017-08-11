package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.service.UserService;
import com.wildbeeslabs.rest.model.User;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * User REST Application Controller
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    /**
     * Default Logger instance
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserService userService;

    /**
     * Get list of users entities
     *
     * @return list of users entities
     */
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        LOGGER.info("Fetching all users");
        List<User> userList = userService.findAllUsers();
        if (userList.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    /**
     * Get user entity by ID
     *
     * @param id - user identifier
     * @return user entity
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        LOGGER.info("Fetching user by id {}", id);
        User user = userService.findById(id);
        if (Objects.isNull(user)) {
            String errorMessage = String.format("ERROR: user with id={%d} is not found", id);
            LOGGER.error(errorMessage);
            return new ResponseEntity(new CustomErrorType(errorMessage), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Create new user entity
     *
     * @param user - user data
     * @param ucBuilder
     * @return response status code
     */
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        LOGGER.info("Creating user : {}", user);
        if (userService.isUserExist(user)) {
            String errorMessage = String.format("ERROR: user with login={%s} already exist", user.getName());
            LOGGER.error(errorMessage);
            return new ResponseEntity(new CustomErrorType(errorMessage), HttpStatus.CONFLICT);
        }
        userService.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Update user entity
     *
     * @param id - user identifier
     * @param user - user entity
     * @return updated user entity
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        LOGGER.info("Updating user by id {}", id);
        User currentUser = userService.findById(id);
        if (Objects.isNull(currentUser)) {
            String errorMessage = String.format("ERROR: user with id={%d} is not found", id);
            LOGGER.error(errorMessage);
            return new ResponseEntity(new CustomErrorType(errorMessage), HttpStatus.NOT_FOUND);
        }
        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());
        userService.updateUser(currentUser);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    /**
     * Delete user entity
     *
     * @param id - user identifier
     * @return response status code
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        LOGGER.info("Deleting user by id {}", id);

        User user = userService.findById(id);
        if (Objects.isNull(user)) {
            String errorMessage = String.format("ERROR: user with id={%d} is not found", id);
            LOGGER.error(errorMessage);
            return new ResponseEntity(new CustomErrorType(errorMessage), HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete all users entities
     *
     * @return response status code
     */
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        LOGGER.info("Deleting all users");

        userService.deleteAllUsers();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
