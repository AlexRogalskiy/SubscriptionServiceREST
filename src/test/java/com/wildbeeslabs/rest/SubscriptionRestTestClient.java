package com.wildbeeslabs.rest;

import com.wildbeeslabs.rest.model.User;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.RestTemplate;

/**
 *
 * Subscription REST Application Test client
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
public class SubscriptionRestTestClient {

    public static final String REST_SERVICE_URI = "http://localhost:8080/rest/api";

    /* GET */
    private static void listAllUsers() {
        System.out.println("Testing listAllUsers API-----------");

        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI + "/user/", List.class);

        if (usersMap != null) {
            usersMap.forEach((map) -> {
                System.out.println("User : id=" + map.get("id") + ", Name=" + map.get("name") + ", Age=" + map.get("age") + ", Salary=" + map.get("salary"));
            });
        } else {
            System.out.println("No user exist----------");
        }
    }

    /* GET */
    private static void getUser() {
        System.out.println("Testing getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject(REST_SERVICE_URI + "/user/1", User.class);
        System.out.println(user);
    }

    /* POST */
    private static void createUser() {
        System.out.println("Testing create User API----------");
        RestTemplate restTemplate = new RestTemplate();

        User user = new User();
        user.setAge(12);
        user.setLogin("Sarah");
        user.setRating(24.3);
        user.setStatus(User.UserStatusType.ACTIVE);

        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/user/", user, User.class);
        System.out.println("Location : " + uri.toASCIIString());
    }

    /* PUT */
    private static void updateUser() {
        System.out.println("Testing update User API----------");
        RestTemplate restTemplate = new RestTemplate();

        User user = new User();
        user.setAge(32);
        user.setLogin("Tomy");
        user.setStatus(User.UserStatusType.ACTIVE);

        restTemplate.put(REST_SERVICE_URI + "/user/1", user);
        System.out.println(user);
    }

    /* DELETE */
    private static void deleteUser() {
        System.out.println("Testing delete User API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI + "/user/3");
    }

    /* DELETE */
    private static void deleteAllUsers() {
        System.out.println("Testing all delete Users API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI + "/user/");
    }

    public static void main(String args[]) {
        /*listAllUsers();
        getUser();
        createUser();
        listAllUsers();
        updateUser();
        listAllUsers();
        deleteUser();
        listAllUsers();
        deleteAllUsers();
        listAllUsers();*/
    }
}
