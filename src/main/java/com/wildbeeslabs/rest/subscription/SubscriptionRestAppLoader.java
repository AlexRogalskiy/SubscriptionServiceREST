package com.wildbeeslabs.rest.subscription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * Initial Subscription REST Application class loader
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@SpringBootApplication(scanBasePackages = {"com.wildbeeslabs.rest.services.subscription"})
public class SubscriptionRestAppLoader {

    public static void main(String[] args) {
        SpringApplication.run(SubscriptionRestAppLoader.class, args);
    }
}
