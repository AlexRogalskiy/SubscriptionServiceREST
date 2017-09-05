package com.wildbeeslabs.rest;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.http.ContentType;
import com.wildbeeslabs.rest.model.Subscription;

import java.util.Date;
import java.util.Objects;

import static junit.framework.TestCase.assertTrue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.nullValue;

import org.junit.Test;

/**
 *
 * SubscriptionController REST Application Test
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
public class SubscriptionControllerTest extends BaseControllerTest {

    @Test
    public void testForbiddenAccess() {
        given().when().get(REST_SERVICE_URI + "/api/subscriptions").then().statusCode(401);
    }

    @Test
    public void testAuthorizationAccess() {
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/subscriptions").then().statusCode(200);
    }

    @Test
    public void testNotFound() {
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/subscriptionss").then().statusCode(404);
    }

    @Test
    public void testVerifySubscription1() {
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/subscription/1").then()
                .body("name", equalTo("subscription1"))
                .body("createdBy", equalTo("admin"))
                .body("expiredAt", nullValue())
                .body("id", equalTo(1))
                .body("createdAt", equalTo(1544562000000L))
                .body("modifiedAt", nullValue())
                .body("type", equalTo(Subscription.SubscriptionStatusType.PREMIUM.toString()))
                .statusCode(200);
    }

    @Test
    public void testAddSubscription() {
        final Subscription subscription = new Subscription();
        subscription.setExpireAt(new Date());
        subscription.setCreatedBy("admin");
        subscription.setName("Guest Group");
        subscription.setType(Subscription.SubscriptionStatusType.STANDARD);

        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123")
                .body(getObjectAsString(subscription))
                .when().post(REST_SERVICE_URI + "/api/subscription").then()
                .statusCode(200);
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/subscriptions").then()
                .body("name", hasItem("Guest Group"))
                .statusCode(200);
    }

    @Test
    public void testUpdateSubscription() {
        final Subscription subscription1 = given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/subscription/1").as(Subscription.class);

        assertTrue(Objects.nonNull(subscription1));
        assertTrue(Objects.equals("subscription1", subscription1.getName()));
        assertTrue(Objects.equals(Subscription.SubscriptionStatusType.PREMIUM, subscription1.getType()));

        subscription1.setExpireAt(parseDate("2019-04-18 00:00:00"));

        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123")
                .body(subscription1)
                .when().put(REST_SERVICE_URI + "/api/subscription/{id}", subscription1.getId()).then()
                .body("expireAt", equalTo(1555534800000L))
                .statusCode(200);
    }

    @Test
    public void testDeleteSubscription() {
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/subscription/4").then()
                .body("name", equalTo("Guest Group"))
                .statusCode(200);
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123")
                .when().delete(REST_SERVICE_URI + "/api/subscription/4").then()
                .statusCode(403);
//        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("dba", "dba123")
//                .when().delete(REST_SERVICE_URI + "/api/subscription/4").then()
//                .statusCode(200);
    }
}
