package com.wildbeeslabs.rest.controller;

import com.wildbeeslabs.rest.controller.proxy.IBaseProxyController;
import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.dto.BaseDTOListWrapper;
import com.wildbeeslabs.rest.model.dto.IBaseDTOListWrapper;
import com.wildbeeslabs.rest.model.dto.SubscriptionDTO;
import com.wildbeeslabs.rest.model.dto.SubscriptionDTOListWrapper;

import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import com.wildbeeslabs.rest.service.interfaces.IBaseService;
import com.wildbeeslabs.rest.service.interfaces.ISubscriptionService;

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

//    @Autowired
//    private ISubscriptionService<T> subscriptionService;
    @Autowired
    private IBaseProxyController<T, E, ISubscriptionService<T>> subscriptionProxyController;

    /**
     * Get list of subscription entities
     *
     * @return list of subscription entities
     */
    @RequestMapping(value = "/subscriptions", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> getAll() {
        List<? extends T> items = getProxyController().getAllItems();
        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(getDTOConverter().convertToDTOAndWrap(items, SubscriptionDTO.class, SubscriptionDTOListWrapper.class), HttpStatus.OK);
    }

    /**
     * Get subscription entity by ID
     *
     * @param id - subscription identifier
     * @return subscription entity
     */
    @RequestMapping(value = "/subscription/{id:[\\d]+}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        T item = getProxyController().getItemById(id);
        return new ResponseEntity<>(getDTOConverter().convertToDTO(item, SubscriptionDTO.class), HttpStatus.OK);
        //return super.getById(id);
    }

    /**
     * Create new subscription entity
     *
     * @param subscriptionDto - subscription data transfer object
     * @return response status code
     */
    @RequestMapping(value = "/subscription", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> create(@RequestBody @Valid E subscriptionDto/*, UriComponentsBuilder ucBuilder*/) {
        T item = getProxyController().createItem(subscriptionDto, (Class<? extends T>) Subscription.class);
        /*
        UriComponentsBuilder bc = UriComponentsBuilder.newInstance();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/subscription/{id}").buildAndExpand(id).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
         */
        return new ResponseEntity<>(HttpStatus.OK);
        //return super.create(subscriptionDto);
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
    @Override
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid E subscriptionDto) {
        T item = getProxyController().updateItem(id, subscriptionDto, (Class<? extends T>) Subscription.class);
        return new ResponseEntity<>(getDTOConverter().convertToDTO(item, (Class<? extends E>) SubscriptionDTO.class), HttpStatus.OK);
        //return super.update(id, subscriptionDto);
    }

    /**
     * Delete subscription entity by ID
     *
     * @param id - subscription identifier
     * @return response status code
     */
    @RequestMapping(value = "/subscription/{id:[\\d]+}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        T item = getProxyController().deleteItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
        //return super.delete(id);
    }

    /**
     * Delete all subscription entities
     *
     * @return response status code
     */
    @RequestMapping(value = "/subscriptions", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    //@ResponseBody
    @Override
    public ResponseEntity<?> deleteAll() {
        getProxyController().deleteAllItems();
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    /**
//     * Get default subscription service instance
//     *
//     * @return subscription service instance
//     */
//    @Override
//    protected ISubscriptionService<T> getDefaultService() {
//        return subscriptionService;
//    }
    /**
     * Get default entity class
     *
     * @return entity class instance
     */
    @Override
    protected Class<? extends T> getEntityClass() {
        return (Class<? extends T>) Subscription.class;
    }

    /**
     * Get default DTO class
     *
     * @return entity class instance
     */
    @Override
    protected Class<? extends E> getDtoClass() {
        return (Class<? extends E>) SubscriptionDTO.class;
    }

    /**
     * Get default DTO Wrapper List class
     *
     * @return entity class instance
     */
    @Override
    protected Class<? extends IBaseDTOListWrapper> getDtoListClass() {
        return SubscriptionDTOListWrapper.class;
    }

    @Override
    protected IBaseProxyController<T, E, ? extends IBaseService<T>> getProxyController() {
        return this.subscriptionProxyController;
    }
}
