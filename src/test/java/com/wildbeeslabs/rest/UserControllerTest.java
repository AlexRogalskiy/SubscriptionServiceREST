package com.wildbeeslabs.rest;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.http.ContentType;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.dto.UserDTO;

import java.util.Objects;

import static junit.framework.TestCase.assertTrue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.nullValue;

import org.junit.Test;

/**
 *
 * UserController REST Application Test
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
public class UserControllerTest extends BaseControllerTest {

    @Test
    public void testForbiddenAccess() {
        given().when().get(REST_SERVICE_URI + "/api/users").then().statusCode(401);
    }

    @Test
    public void testAuthorizationAccess() {
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/users").then().statusCode(200);
    }

    @Test
    public void testNotFound() {
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/userss").then().statusCode(404);
    }

    @Test
    public void testVerifyUser2() {
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/user/2").then()
                .body("login", equalTo("user2@gmail.com"))
                .body("name", equalTo("user2"))
                .body("createdBy", equalTo("user2@gmail.com"))
                .body("age", equalTo(26))
                .body("phone", equalTo("+79211234567"))
                .body("gender", equalTo(User.UserGenderType.MALE.toString()))
                .body("id", equalTo(2))
                .body("createdAt", equalTo("2017-04-30 00:00:00"))
                .body("modifiedAt", nullValue())
                .body("rating", equalTo(1.0f))
                .body("registeredAt", equalTo("2017-04-30 00:00:00"))
                .body("status", equalTo(User.UserStatusType.ACTIVE.toString()))
                .statusCode(200);
    }

    @Test
    public void testAddUser() {
        final UserDTO user = new UserDTO();
        user.setAge(25);
        user.setCreatedBy("user18@gmail.com");
        user.setCreatedAt("2017-04-18 00:00:00");
        user.setRegisteredAt("2016-04-18 00:00:00");
        user.setLogin("user18@gmail.com");
        user.setName("user1");
        user.setGender(User.UserGenderType.MALE);
        user.setRating(1.00);
        user.setPhone("+79211234567");
        user.setRegisteredAt("2017-04-18 00:00:00");
        user.setStatus(User.UserStatusType.UNVERIFIED);

        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123")
                .body(getObjectAsString(user))
                .when().post(REST_SERVICE_URI + "/api/user").then()
                .statusCode(200);
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/users").then()
                .body("login", hasItem("user18@gmail.com"))
                .statusCode(200);
    }

    @Test
    public void testUpdateUser() {
        final UserDTO user1 = given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/user/1").as(UserDTO.class);

        assertTrue(Objects.nonNull(user1));
        assertTrue(Objects.equals("user1@gmail.com", user1.getLogin()));
        assertTrue(Objects.equals(User.UserStatusType.UNVERIFIED, user1.getStatus()));

        user1.setStatus(User.UserStatusType.ACTIVE);

        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123")
                .body(user1)
                .when().put(REST_SERVICE_URI + "/api/user/{id}", user1.getId()).then()
                .body("status", equalTo(User.UserStatusType.ACTIVE.toString()))
                .statusCode(200);
    }

    @Test
    public void testDeleteUser() {
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/api/user/4").then()
                .body("login", equalTo("user18@gmail.com"))
                .statusCode(200);
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123")
                .when().delete(REST_SERVICE_URI + "/api/user/4").then()
                .statusCode(403);
//        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("dba", "dba123")
//                .when().delete(REST_SERVICE_URI + "/api/user/4").then()
//                .statusCode(200);
    }
}
