package com.wildbeeslabs.rest;

import com.wildbeeslabs.rest.controller.UserController;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.service.interfaces.UserService;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * Subscription REST Application Test
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserRestApiTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService usernService;

    @Test
    public void testGetAllUsers() throws Exception {
        given(this.usernService.findAll()).willReturn(new ArrayList<>());
        this.mvc.perform(get("/sboot/vehicle").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk()).andExpect(content().string("Honda Civic"));
    }

    @Test
    public void testGetUserById() throws Exception {
        given(this.usernService.findById(new Long(1))).willReturn(new User());
        this.mvc.perform(get("/sboot/vehicle").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk()).andExpect(content().string("Honda Civic"));
    }
}
