//package com.wildbeeslabs.rest;
//
//import com.wildbeeslabs.rest.controller.SubscriptionController;
//import com.wildbeeslabs.rest.model.Subscription;
//import com.wildbeeslabs.rest.service.SubscriptionServiceImpl;
//import com.wildbeeslabs.rest.service.interfaces.SubscriptionService;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
///**
// *
// * Subscription REST Application Test
// *
// * @author Alex
// * @version 1.0.0
// * @since 2017-08-08
// */
////@RunWith(SpringRunner.class)
////@WebMvcTest(controllers = {SubscriptionController.class, SubscriptionServiceImpl.class})
//public class SubscriptionControllerTest {
//
//    public static final String REST_SERVICE_URI = "http://localhost:8080/newsletterSub/api";
//
//    @Autowired
//    private MockMvc mvc;
//    @Autowired
//    private SubscriptionService<Subscription> subscriptionService;
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
//    @Test
//    public void testGetAllSubscriptions() throws Exception {
//        //given(this.subscriptionService.findAll()).willReturn(new ArrayList<>());
//        //this.mvc.perform(get("/sboot/vehicle").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk()).andExpect(content().string("Honda Civic"));
//    }
//
//    @Test
//    public void testGetSubscriptionById() throws Exception {
//        //given(this.subscriptionService.findById(new Long(1))).willReturn(new Subscription());
//        //this.mvc.perform(get("/sboot/vehicle").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk()).andExpect(content().string("Honda Civic"));
//    }
//}
