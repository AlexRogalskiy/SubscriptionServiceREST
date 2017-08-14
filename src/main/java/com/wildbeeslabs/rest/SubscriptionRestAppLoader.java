package com.wildbeeslabs.rest;

import com.wildbeeslabs.rest.configuration.JpaConfiguration;
import com.wildbeeslabs.rest.configuration.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 *
 * Initial Subscription REST Application class loader
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@SpringBootApplication(scanBasePackages = {"com.wildbeeslabs.rest"})
@Import({JpaConfiguration.class, SecurityConfiguration.class})
public class SubscriptionRestAppLoader {

    public static void main(String[] args) {
        SpringApplication.run(SubscriptionRestAppLoader.class, args);
    }
}
