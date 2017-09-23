package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.api.rest.common.controller.ABaseController;
import com.wildbeeslabs.api.rest.common.exception.EmptyContentException;

import com.wildbeeslabs.rest.controller.proxy.UserProxyController;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.SubscriptionStatusInfo;
import com.wildbeeslabs.rest.model.dto.UserDTO;

import java.util.Date;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api")
public class UserController<T extends User, E extends UserDTO> extends ABaseController<T, E, Long, UserProxyController<T, E>> {

    @InitBinder
    public void initBinder(final WebDataBinder dataBinder) {
        BaseEnumConverter<SubscriptionStatusInfo.SubscriptionStatusType> converter = new BaseEnumConverter<>(SubscriptionStatusInfo.SubscriptionStatusType.class);
        dataBinder.registerCustomEditor(SubscriptionStatusInfo.SubscriptionStatusType.class, converter);
    }

    /**
     * Get list of user entities by subscription type / date / date order
     *
     * @param status - user status
     * @param subStatus - subscription status
     * @param subDate - subscription date
     * @param subDateOrder - date order (before / after)
     * @return list of user entities
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getAll(@RequestParam(name = "status", required = false) User.UserStatusType status, @RequestParam(name = "subdate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date subDate, @RequestParam(name = "substatus", required = false) SubscriptionStatusInfo.SubscriptionStatusType subStatus, @RequestParam(name = "order", required = false, defaultValue = "false") Boolean subDateOrder) {
        try {
            return new ResponseEntity<>(getProxyController().findAll(status, subDate, subStatus, subDateOrder), HttpStatus.OK);
        } catch (EmptyContentException ex) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Get user entity by ID
     *
     * @param id - user identifier
     * @return user entity
     */
    @RequestMapping(value = "/user/{id:[\\d]+}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return super.getById(id);
    }

    /**
     * Create new user entity
     *
     * @param userDto - user data transfer object
     * @return response status code
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> create(@RequestBody @Valid E userDto) {
        /*
        UriComponentsBuilder bc = UriComponentsBuilder.newInstance();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(request.getRequestURI() + "/{id}").buildAndExpand(userDto.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
         */
        return super.create(userDto);
    }

    /**
     * Update user entity
     *
     * @param id - user identifier
     * @param userDto - user data transfer object
     * @return updated user entity
     */
    @RequestMapping(value = "/user/{id:[\\d]+}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid E userDto) {
        return super.update(id, userDto);
    }

    /**
     * Delete user entity
     *
     * @param id - user identifier
     * @return response status code
     */
    @RequestMapping(value = "/user/{id:[\\d]+}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return super.delete(id);
    }

    /**
     * Delete all user entities
     *
     * @return response status code
     */
    @RequestMapping(value = "/users", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    //@ResponseBody
    @Override
    public ResponseEntity<?> deleteAll() {
        return super.deleteAll();
    }
}
