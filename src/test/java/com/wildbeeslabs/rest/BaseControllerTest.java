package com.wildbeeslabs.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.wildbeeslabs.rest.controller.SubscriptionController;
import com.wildbeeslabs.rest.controller.UserController;
import com.wildbeeslabs.rest.controller.UserSubscriptionController;

import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * BaseController REST Application Test
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SubscriptionRestAppLoader.class, UserController.class, SubscriptionController.class, UserSubscriptionController.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
abstract public class BaseControllerTest {

    /**
     * Default Logger instance
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    /**
     * Default REST URI bind address
     */
    @Value("#{'${server.basePath}'.concat(':').concat('${server.port}').concat('${server.contextPath}')}")
    protected String REST_SERVICE_URI;

    protected String getObjectAsString(final Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        String value = null;
        try {
            value = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Cannot serialize entity, message={}", ex.getMessage());
        }
        return value;
    }

//    @Before
//    public void setUp() { 
//        RestAssured.authentication = preemptive().basic("user", "123");
//    }
}
