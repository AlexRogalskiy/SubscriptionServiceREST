package com.wildbeeslabs.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildbeeslabs.rest.controller.SubscriptionController;
import com.wildbeeslabs.rest.controller.UserController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SubscriptionRestAppLoader.class, UserController.class, SubscriptionController.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
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

    /**
     * Default date format
     */
    protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    protected Date parseDate(final String date) {
        return this.parseDate(date, DATE_FORMAT);
    }

    protected Date parseDate(final String date, final String format) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.parse(date);
        } catch (ParseException ex) {
            LOGGER.error("ERROR: cannot parse input date={} by format={}", date, format);
            return null;
        }
    }

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
}
