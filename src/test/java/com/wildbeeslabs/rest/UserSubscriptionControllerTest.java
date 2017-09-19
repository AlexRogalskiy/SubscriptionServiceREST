package com.wildbeeslabs.rest;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;

import com.wildbeeslabs.rest.model.SubscriptionStatusInfo;
import com.wildbeeslabs.rest.model.dto.SubscriptionDTO;
import com.wildbeeslabs.rest.model.dto.UserDTO;
import com.wildbeeslabs.rest.model.dto.UserSubOrderDTO;

import java.util.Objects;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.hasItem;

import org.junit.Test;

/**
 *
 * UserSubscriptionController REST Application Test
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
public class UserSubscriptionControllerTest extends BaseControllerTest {

    @Test
    public void testAddUserSubscription() {
        final UserDTO user1 = given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(getServiceURI() + "/api/user/1").as(UserDTO.class);
        final SubscriptionDTO subscription3 = given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(getServiceURI() + "/api/subscription/3").as(SubscriptionDTO.class);

        assertTrue(Objects.equals("user1@gmail.com", user1.getLogin()));
        //assertTrue(Objects.equals(User.UserStatusType.UNVERIFIED, user1.getStatus()));
        assertTrue(Objects.equals("subscription3", subscription3.getName()));
        assertTrue(Objects.equals(SubscriptionStatusInfo.SubscriptionStatusType.STANDARD, subscription3.getStatus()));

        final UserSubOrderDTO userSubOrder = new UserSubOrderDTO();
        userSubOrder.setUser(user1);
        userSubOrder.setSubscription(subscription3);
        userSubOrder.setCreatedBy(user1.getLogin());
        userSubOrder.setSubscribedAt("2017-05-28 00:00:00");

        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123")
                .body(userSubOrder)
                .when().post(getServiceURI() + "/api/user/{id}/subscription", user1.getId()).then()
                .statusCode(201);
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(getServiceURI() + "/api/user/{id}/subscriptions", user1.getId()).then()
                .body("name", hasItem("subscription3"))
                .statusCode(200);
    }
}
