package com.wildbeeslabs.rest;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.http.ContentType;
import com.wildbeeslabs.rest.model.SubscriptionStatusInfo;
import com.wildbeeslabs.rest.model.dto.SubscriptionDTO;

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
        //.auth().digest( ADMIN_USERNAME, ADMIN_PASSWORD )
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
                //                .body("expireAt", equalTo(1544562000000L))
                .body("expireAt", equalTo("2018-12-12 00:00:00"))
                .body("id", equalTo(1))
                .body("createdAt", equalTo("2018-12-12 00:00:00"))
                .body("modifiedAt", nullValue())
                .body("type", equalTo(SubscriptionStatusInfo.SubscriptionStatusType.PREMIUM.toString()))
                .statusCode(200);
    }

    @Test
    public void testAddSubscription() {
        final SubscriptionDTO subscription = new SubscriptionDTO();
        //subscription.setExpireAt(DateUtils.strToDate("2018-08-28 00:00:00"));
        subscription.setExpireAt("2018-08-28 00:00:00");
        subscription.setCreatedBy("admin");
        subscription.setName("Guest Group");
        //subscription.setPrefix('standard');
        subscription.setStatus(SubscriptionStatusInfo.SubscriptionStatusType.STANDARD);

        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123")
                .body(getObjectAsString(subscription))
                .when().post(REST_SERVICE_URI + "/api/subscription").then()
                .statusCode(201);
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/subscriptions").then()
                .body("name", hasItem("Guest Group"))
                .statusCode(200);
    }

    @Test
    public void testUpdateSubscription() {
        final SubscriptionDTO subscription1 = given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/subscription/1").as(SubscriptionDTO.class);

        assertTrue(Objects.nonNull(subscription1));
        assertTrue(Objects.equals("subscription1", subscription1.getName()));
        assertTrue(Objects.equals(SubscriptionStatusInfo.SubscriptionStatusType.PREMIUM, subscription1.getStatus()));

        //subscription1.setExpireAt(DateUtils.strToDate("2019-04-18 00:00:00"));
        subscription1.setExpireAt("2019-04-18 00:00:00");

        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123")
                .body(subscription1)
                .when().put(REST_SERVICE_URI + "/api/subscription/{id}", subscription1.getId()).then()
                //                .body("expireAt", equalTo(1555534800000L))
                .body("expireAt", equalTo("2019-04-18 00:00:00"))
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
