package com.wildbeeslabs.rest;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;

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
        final User user1 = given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/user/1").as(User.class);
        final Subscription subscription3 = given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/subscription/3").as(Subscription.class);

        assertTrue(Objects.equals("user1@gmail.com", user1.getLogin()));
        //assertTrue(Objects.equals(User.UserStatusType.UNVERIFIED, user1.getStatus()));
        assertTrue(Objects.equals("subscription3", subscription3.getName()));
        assertTrue(Objects.equals(Subscription.SubscriptionStatusType.STANDARD, subscription3.getType()));

        final UserSubOrder userSubOrder = new UserSubOrder();
        userSubOrder.setUser(user1);
        userSubOrder.setCreatedBy(user1.getLogin());
        userSubOrder.setSubscription(subscription3);
        userSubOrder.setSubscribedAt(parseDate("2017-05-28 00:00:00"));

        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123")
                .body(userSubOrder)
                .when().post(REST_SERVICE_URI + "/api/user/{id}/subscription", user1.getId()).then()
                .statusCode(200);
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/user/{id}/subscriptions", user1.getId()).then()
                .body("name", hasItem("subscription3"))
                .statusCode(200);
    }
}
