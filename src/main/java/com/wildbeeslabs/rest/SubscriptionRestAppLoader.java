package com.wildbeeslabs.rest;

import com.wildbeeslabs.rest.configuration.SecurityConfiguration;
import com.wildbeeslabs.rest.configuration.AppConfiguration;
import com.wildbeeslabs.rest.configuration.JpaConfiguration;
import com.wildbeeslabs.rest.configuration.ValidatorConfiguration;
import com.wildbeeslabs.rest.configuration.WebConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 *
 * Initial Newsletter Subscription REST Application class loader
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@SpringBootApplication(scanBasePackages = {"com.wildbeeslabs.rest", "com.wildbeeslabs.api.rest.common"})
//@ComponentScan({"com.wildbeeslabs.rest", "com.wildbeeslabs.api.rest.common"})
@Import({JpaConfiguration.class, SecurityConfiguration.class, AppConfiguration.class, WebConfiguration.class, ValidatorConfiguration.class})
public class SubscriptionRestAppLoader {

    public static void main(String[] args) {
        SpringApplication.run(SubscriptionRestAppLoader.class, args);
    }
}
