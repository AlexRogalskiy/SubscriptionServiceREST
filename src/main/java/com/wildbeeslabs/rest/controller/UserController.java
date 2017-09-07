package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.service.interfaces.UserService;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.dto.UserDTO;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
 * @param <E>
 */
@RestController
//@Validated
@RequestMapping("/api")
public class UserController<T extends User, E extends UserDTO> extends ABaseController<T, E> {

    @Autowired
    private UserService<T> userService;

    @InitBinder
    public void initBinder(final WebDataBinder dataBinder) {
        BaseEnumConverter<Subscription.SubscriptionStatusType> converter = new BaseEnumConverter<>(Subscription.SubscriptionStatusType.class);
        dataBinder.registerCustomEditor(Subscription.SubscriptionStatusType.class, converter);
    }

    /**
     * Get list of user entities by subscription type / date / date order
     *
     * @param subType - subscription type
     * @param subDate - subscription date
     * @param subDateOrder - date order (before / after)
     * @return list of user entities
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date subDate, @RequestParam(name = "type", required = false) Subscription.SubscriptionStatusType subType, @RequestParam(name = "order", required = false, defaultValue = "false") Boolean subDateOrder) {
        LOGGER.info("Fetching all users by subscription date {}, type {}, date order {} (1 - before, 0 - after)", subDate, subType, subDateOrder);

        UserService.DateTypeOrder dateTypeOrder = Objects.equals(subDateOrder, Boolean.TRUE) ? UserService.DateTypeOrder.AFTER : UserService.DateTypeOrder.BEFORE;
        List<T> userList = getDefaultService().findAllBySubscriptionTypeAndDate(subDate, subType, dateTypeOrder);
        if (userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userList.stream().map(item -> convertToDTO(item, getDtoClass())).collect(Collectors.toList()), HttpStatus.OK);
    }

    /**
     * Get user entity by ID
     *
     * @param id - user identifier
     * @return user entity
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        return super.getById(id);
    }

    /**
     * Create new user entity
     *
     * @param userDto - user data transfer object
     * @param ucBuilder - URI component builder
     * @return response status code
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody @Valid E userDto, UriComponentsBuilder ucBuilder) {
        return super.create(userDto);
    }

    /**
     * Update user entity
     *
     * @param id - user identifier
     * @param userDto - user data transfer object
     * @return updated user entity
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody @Valid E userDto) {
        return super.update(id, userDto);
    }

    /**
     * Delete user entity
     *
     * @param id - user identifier
     * @return response status code
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return super.delete(id);
    }

    /**
     * Delete all user entities
     *
     * @return response status code
     */
    @RequestMapping(value = "/users", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> deleteAllUsers() {
        return super.deleteAll();
    }

    /**
     * Get default user service instance
     *
     * @return user service instance
     */
    @Override
    protected UserService<T> getDefaultService() {
        return userService;
    }

    @Override
    protected Class<T> getEntityClass() {
        return (Class<T>) User.class;
    }

    @Override
    protected Class<E> getDtoClass() {
        return (Class<E>) UserDTO.class;
    }
}
