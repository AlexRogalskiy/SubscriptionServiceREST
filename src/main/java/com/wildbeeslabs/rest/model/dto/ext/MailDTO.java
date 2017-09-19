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
package com.wildbeeslabs.rest.model.dto.ext;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * MailDTO REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
public class MailDTO implements Serializable {

    private String from;

    private final Set<String> to = new HashSet<>();

    private final Set<String> cc = new HashSet<>();

    private String subject;

    private String message;

    private boolean isHtml;

    public MailDTO() {
    }

    public MailDTO(final String from, final String toList, final String subject, final String message) {
        this(from, toList, null, subject, message);
    }

    public MailDTO(final String from, final String toList, final String ccList, final String subject, final String message) {
        this.from = from;
        this.subject = subject;
        this.message = message;
        if (Objects.nonNull(toList)) {
            this.to.addAll(Arrays.asList(splitByComma(toList)));
        }
        if (Objects.nonNull(ccList)) {
            this.cc.addAll(Arrays.asList(splitByComma(ccList)));
        }
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(final String from) {
        this.from = from;
    }

    /**
     * @return the to
     */
    public Set<String> getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(final Set<String> to) {
        this.to.clear();
        if (Objects.nonNull(to)) {
            this.to.addAll(to);
        }
    }

    public void addMailTo(final String mailTo) {
        if (Objects.nonNull(mailTo)) {
            this.to.add(mailTo);
        }
    }

    /**
     * @return the cc
     */
    public Set<String> getCc() {
        return cc;
    }

    /**
     * @param cc the cc to set
     */
    public void setCc(final Set<String> cc) {
        this.cc.clear();
        if (Objects.nonNull(to)) {
            this.cc.addAll(cc);
        }
    }

    public void addMailCc(final String mailCc) {
        if (Objects.nonNull(mailCc)) {
            this.cc.add(mailCc);
        }
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(final String subject) {
        this.subject = subject;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * @return the isHtml
     */
    public boolean isHtml() {
        return isHtml;
    }

    /**
     * @param isHtml the isHtml to set
     */
    public void setHtml(final boolean isHtml) {
        this.isHtml = isHtml;
    }

    private String[] splitByComma(final String toMultiple) {
        if (Objects.isNull(toMultiple)) {
            return null;
        }
        String[] toSplit = toMultiple.split(",");
        return toSplit;
    }

    public String getToAsList() {
        return StringUtils.join(this.to, ",");
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.from);
        hash = 97 * hash + Objects.hashCode(this.to);
        hash = 97 * hash + Objects.hashCode(this.cc);
        hash = 97 * hash + Objects.hashCode(this.subject);
        hash = 97 * hash + Objects.hashCode(this.message);
        hash = 97 * hash + (this.isHtml ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        final MailDTO other = (MailDTO) obj;
        if (this.isHtml != other.isHtml) {
            return false;
        }
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (!Objects.equals(this.to, other.to)) {
            return false;
        }
        return Objects.equals(this.cc, other.cc);
    }

    @Override
    public String toString() {
        return String.format("MailDTO {from: %s, to: %s, cc: %s, subject: %s, message: %s, isHtml: %s}", this.from, this.to, this.cc, this.subject, this.message, this.isHtml);
    }
}
