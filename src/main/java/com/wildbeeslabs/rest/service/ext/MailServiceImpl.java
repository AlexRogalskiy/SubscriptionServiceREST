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
package com.wildbeeslabs.rest.service.ext;

import com.wildbeeslabs.rest.model.dto.ext.MailDTO;
import com.wildbeeslabs.rest.service.interfaces.ext.IMailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Mail REST Application Service implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
//@Service("mailService")
//@Transactional
public class MailServiceImpl<T extends MailDTO> implements IMailService<T> {

    /**
     * Default Logger instance
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    //@Autowired
    private JavaMailSender mailSender;

    @Override
    public void send(final T mailDto) {
        if (mailDto.isHtml()) {
            try {
                this.sendHtmlMail(mailDto);
            } catch (MessagingException e) {
                LOGGER.error("Could not send email to: {}, error: {}", mailDto.getToAsList(), e.getMessage());
            }
        } else {
            this.sendPlainTextMail(mailDto);
        }
    }

    private void sendHtmlMail(final T mailDto) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(mailDto.getTo().toArray(new String[mailDto.getTo().size()]));
        helper.setReplyTo(mailDto.getFrom());
        helper.setFrom(mailDto.getFrom());
        helper.setSubject(mailDto.getSubject());
        helper.setText(mailDto.getMessage(), true);

        if (mailDto.getCc().size() > 0) {
            helper.setCc(mailDto.getCc().toArray(new String[mailDto.getCc().size()]));
        }
        this.mailSender.send(message);
    }

    private void sendPlainTextMail(final T mailDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailDto.getTo().toArray(new String[mailDto.getTo().size()]);
        mailMessage.setTo(mailDto.getTo().toArray(new String[mailDto.getTo().size()]));
        mailMessage.setReplyTo(mailDto.getFrom());
        mailMessage.setFrom(mailDto.getFrom());
        mailMessage.setSubject(mailDto.getSubject());
        mailMessage.setText(mailDto.getMessage());
        if (mailDto.getCc().size() > 0) {
            mailMessage.setCc(mailDto.getCc().toArray(new String[mailDto.getCc().size()]));
        }
        this.mailSender.send(mailMessage);
    }
}
