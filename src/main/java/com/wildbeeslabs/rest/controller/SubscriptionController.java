package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.service.interfaces.SubscriptionService;
import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.dto.BaseDTOListWrapper;
import com.wildbeeslabs.rest.model.dto.SubscriptionDTO;
import com.wildbeeslabs.rest.model.dto.SubscriptionDTOListWrapper;
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
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * Subscription REST Controller implementation
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
public class SubscriptionController<T extends Subscription, E extends SubscriptionDTO> extends ABaseController<T, E> {

    @Autowired
    private SubscriptionService<T> subscriptionService;

    /**
     * Get list of subscription entities
     *
     * @return list of subscription entities
     */
    @RequestMapping(value = "/subscriptions", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getAllSubscriptions() {
        return super.getAll();
    }

    /**
     * Get subscription entity by ID
     *
     * @param id - subscription identifier
     * @return subscription entity
     */
    @RequestMapping(value = "/subscription/{id:[\\d]+}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> getSubscriptionById(@PathVariable("id") Long id) {
        return super.getById(id);
    }

    /**
     * Create new subscription entity
     *
     * @param subscriptionDto - subscription data transfer object
     * @param ucBuilder - URI component builder
     * @return response status code
     */
    @RequestMapping(value = "/subscription", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> createSubscription(@RequestBody @Valid E subscriptionDto, UriComponentsBuilder ucBuilder) {
        return super.create(subscriptionDto);
    }

    /**
     * Update subscription entity by ID
     *
     * @param id - subscription identifier
     * @param subscriptionDto - subscription data transfer object
     * @return updated subscription entity
     */
    @RequestMapping(value = "/subscription/{id:[\\d]+}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> updateSubscription(@PathVariable("id") Long id, @RequestBody @Valid E subscriptionDto) {
        return super.update(id, subscriptionDto);
    }

    /**
     * Delete subscription entity by ID
     *
     * @param id - subscription identifier
     * @return response status code
     */
    @RequestMapping(value = "/subscription/{id:[\\d]+}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> deleteSubscription(@PathVariable("id") Long id) {
        return super.delete(id);
    }

    /**
     * Delete all subscription entities
     *
     * @return response status code
     */
    @RequestMapping(value = "/subscriptions", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
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

    /**
     * Get default entity class instance
     *
     * @return entity class instance
     */
    @Override
    protected Class<? extends T> getEntityClass() {
        return (Class<? extends T>) Subscription.class;
    }

    /**
     * Get default DTO class instance
     *
     * @return entity class instance
     */
    @Override
    protected Class<? extends E> getDtoClass() {
        return (Class<? extends E>) SubscriptionDTO.class;
    }

    @Override
    protected Class<? extends BaseDTOListWrapper> getDtoListClass() {
        return SubscriptionDTOListWrapper.class;
    }
}
