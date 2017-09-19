/*
 * The MIT License
 *
 * Copyright 2017 Pivotal Software, Inc..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.rest.controller.proxy.ext;

import com.wildbeeslabs.rest.model.dto.ext.MailDTO;
import com.wildbeeslabs.rest.model.dto.ext.MailTemplate;
import com.wildbeeslabs.rest.service.interfaces.ext.IMailService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 *
 * Mail Proxy Controller implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Component
public class MailProxyController {//implements ApplicationRunner {

    @Autowired
    private IMailService mailService;

//    @Override
//    public void run(final ApplicationArguments args) throws Exception {
//        sendHtmltMail();
//        sendTextMail();
//    }
    public void sendTextMail() {
        String from = "pavan@localhost";
        String to = "solapure@localhost";
        String subject = "Java Mail with Spring Boot - Plain Text";

        MailTemplate template = new MailTemplate("premium");
        Map<String, String> replacements = new HashMap<>();
        replacements.put("user", "Pavan");
        replacements.put("today", String.valueOf(new Date()));
        String message = template.getTemplate(/*replacements*/);

        MailDTO mailDto = new MailDTO(from, to, subject, message);
        this.mailService.send(mailDto);
    }

    public void sendHtmltMail() {
        String from = "pavan@localhost";
        String to = "solapure@localhost";
        String subject = "Java Mail with Spring Boot";

        MailTemplate template = new MailTemplate("standard");
        Map<String, String> replacements = new HashMap<>();
        replacements.put("user", "Pavan");
        replacements.put("today", String.valueOf(new Date()));
        String message = template.getTemplate(/*replacements*/);

        MailDTO mailDto = new MailDTO(from, to, subject, message);
        mailDto.setHtml(true);
        this.mailService.send(mailDto);
    }
}
