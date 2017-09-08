package com.wildbeeslabs.rest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * Default REST Controller implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Controller
public class DefaultRestController {

    @Value("${spring.application.name}")
    private String appName;

    @RequestMapping("/api")
    public String homePage(final Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }

//    @RequestMapping(value = "/", method = RequestMethod.POST)
//    public String submitForm(final @Valid Subscriber subscriber, final BindingResult result, final Model m) {
//        if (result.hasErrors()) {
//            return "formPage";
//        }
//        m.addAttribute("message", "Successfully saved person: " + subscriber.toString());
//        return "formPage";
//    }
}
