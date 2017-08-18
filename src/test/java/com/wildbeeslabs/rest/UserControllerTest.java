package com.wildbeeslabs.rest;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import static com.wildbeeslabs.rest.SubscriptionRestTestClient.REST_SERVICE_URI;
//import com.wildbeeslabs.rest.controller.UserController;
//import com.wildbeeslabs.rest.model.User;
//import com.wildbeeslabs.rest.repositories.UserRepository;
//import com.wildbeeslabs.rest.service.UserServiceImpl;
//import com.wildbeeslabs.rest.service.interfaces.UserService;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//
//import java.util.Date;
//import org.junit.Before;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static org.mockito.BDDMockito.given;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.Matchers.*;
//import org.mockito.Mockito;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.Mockito.when;
//import org.mockito.MockitoAnnotations;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
///**
// *
// * Subscription REST Application Test
// *
// * @author Alex
// * @version 1.0.0
// * @since 2017-08-08
// */
//@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = {UserController.class, UserServiceImpl.class}, secure = false)
//@WebAppConfiguration
//@EnableWebMvc
////@ContextConfiguration(classes = {WebConfig.class})
//public class UserControllerTest {
//
//    public static final String REST_SERVICE_URI = "http://localhost:8080/newsletterSub/api";
//
//    @Autowired
//    private MockMvc mvc;
//    @Autowired
//    private UserController userController;
//    @Autowired
//    private WebApplicationContext wac;
//
//    private User user;
//
//    private Date parseDate(final String date) {
//        try {
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            return df.parse(date);
//        } catch (ParseException ex) {
//            return null;
//        }
//    }
//
//    @Before
//    public void prepare() {
//        user = new User();
//        user.setId(1L);
//        user.setAge(25);
//        user.setCreatedAt(parseDate("2017-04-18 00:00:00"));
//        user.setLogin("user1@gmail.com");
//        user.setModifiedAt(null);
//        user.setRating(1.00);
//        user.setRegisteredAt(parseDate("2017-04-18 00:00:00"));
//        user.setStatus(User.UserStatusType.UNVERIFIED);
//
//        //MockitoAnnotations.initMocks(this);
//        //this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
//        this.mvc = MockMvcBuilders.standaloneSetup(userController).build();
//    }
//
//    @Test
//    public void testGetUserById() throws Exception {
//        //MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(REST_SERVICE_URI + "/user/1");
//        //ResultActions result = this.mvc.perform(request);
//
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.APPLICATION_JSON);
////        RestTemplate restTemplate = new RestTemplate();
////        String url = "http://localhost:8080/newsletterSub/api/user/1";
////        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
////        ResponseEntity<User> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, User.class, 1);
////        User article = responseEntity.getBody();
////        System.out.println(article);
//
//        //UserController mock = org.mockito.Mockito.mock(UserController.class);
//        //when(mock.getUserById(1L)).thenReturn(user);
//        //verify(mock, times(1)).findById(1L);
//        //verifyNoMoreInteractions(mock);
//        this.mvc.perform(get("api/user/{id}", 1)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.createdAt", is(1492462800000L)))
//                .andExpect(jsonPath("$.modifiedAt", nullValue()))
//                .andExpect(jsonPath("$.id", is(1L)))
//                .andExpect(jsonPath("$.login", is("user1@gmail.com")))
//                .andExpect(jsonPath("$.age", is(25)))
//                .andExpect(jsonPath("$.rating", is(1)))
//                .andExpect(jsonPath("$.registeredAt", is(1492462800000L)))
//                .andExpect(jsonPath("$.status", is("UNVERIFIED")));
//    }
//
//    @Test
//    public void testGetUserByIdNotFound() throws Exception {
////        this.mvc.perform(get(REST_SERVICE_URI + "/user/25").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void testCreateUser() throws Exception {
//        User user = new User();
//        user.setAge(25);
//        user.setCreatedAt(parseDate("2017-04-18 00:00:00"));
//        user.setLogin("user18@gmail.com");
//        user.setModifiedAt(null);
//        user.setRating(1.00);
//        user.setRegisteredAt(parseDate("2017-04-18 00:00:00"));
//        user.setStatus(User.UserStatusType.UNVERIFIED);
//
////        userRepository.save(user);
////
////        ObjectMapper mapper = new ObjectMapper();
////        this.mvc.perform(post(REST_SERVICE_URI + "/user")
////                .contentType(MediaType.APPLICATION_JSON)
////                .accept(MediaType.APPLICATION_JSON)
////                .content(mapper.writeValueAsString(user)))
////                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        //this.mvc.perform(get("/sboot/vehicle").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk()).andExpect(content().string("Honda Civic"));
//        //this.mvc.perform(get(REST_SERVICE_URI + "/user/25").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());
//    }
//}

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.wildbeeslabs.rest.configuration.JpaConfiguration;
import com.wildbeeslabs.rest.configuration.SecurityConfiguration;
import com.wildbeeslabs.rest.controller.UserController;
import com.wildbeeslabs.rest.service.UserServiceImpl;
import com.wildbeeslabs.rest.service.interfaces.UserService;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = {UserController.class, UserServiceImpl.class}, secure = false)
//@WebAppConfiguration
//@EnableWebMvc
@SpringBootTest(classes = {SubscriptionRestAppLoader.class, UserController.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import({JpaConfiguration.class, SecurityConfiguration.class})
@AutoConfigureMockMvc
public class UserControllerTest {

    public static final String REST_SERVICE_URI = "http://localhost:8080/newsletterSub/api";

    @Test
    public void makeSureThatGoogleIsUp() {
        given().when().get("http://www.google.com").then().statusCode(200);
    }

    @Test
    public void testForbiddenAccess() {
        given().when().get(REST_SERVICE_URI + "/users").then().statusCode(401);
    }

    @Test
    public void testAuthorizationAccess() {
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/users").then().statusCode(200);
    }

    @Test
    public void testNotFound() {
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/userss").then().statusCode(404);
    }

    @Test
    public void testVerifyUser1() {
        given().contentType(ContentType.JSON).accept(ContentType.JSON).auth().basic("user", "user123").when().get(REST_SERVICE_URI + "/user/1").then()
                .body("login", equalTo("user1@gmail.com"))
                .body("age", equalTo(25))
                .body("id", equalTo(1))
                .body("createdAt", equalTo(1492462800000L))
                .body("modifiedAt", nullValue())
                .body("rating", equalTo(1.0f))
                .body("registeredAt", equalTo(1492462800000L))
                .body("status", equalTo("UNVERIFIED"))
                .statusCode(200);
    }
}
