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
package com.wildbeeslabs.rest.model.dto.mail;

import org.springframework.ui.Model;

/**
 *
 * MailTemplate REST model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
public class MailTemplate {

    /**
     * Default mail template suffix
     */
    private static final String DEFAULT_MAIL_TEMPLATE_SUFFIX = "_mail";

    private String templateId;
    private String template;

//    private Map<String, String> replacementParams;
    public MailTemplate(final String templateId) {
        this.templateId = templateId;
//        try {
//            this.template = loadTemplate(templateId);
//        } catch (Exception e) {
//            this.template = Constants.BLANK;
//        }
    }

    public String getTemplate(final Model model) {
        model.addAttribute("appName", "test");
        return this.getTemplateId() + DEFAULT_MAIL_TEMPLATE_SUFFIX;
    }

//    private String loadTemplate(String templateId) throws Exception {
//        ClassLoader classLoader = getClass().getClassLoader();
//        File file = new File(classLoader.getResource("templates/" + templateId + "_mail").getFile());
//        String content = Constants.BLANK;
//        try {
//            content = new String(Files.readAllBytes(file.toPath()));
//        } catch (IOException e) {
//            throw new ServiceException(String.format(getLocaleMessage("error.no.mail.template.id"), templateId));
//        }
//        return content;
//    }
//    public String getTemplate(Map<String, String> replacements) {
//        String cTemplate = this.getTemplate();
//
//        if (!AppUtil.isObjectEmpty(cTemplate)) {
//            for (Map.Entry<String, String> entry : replacements.entrySet()) {
//                cTemplate = cTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
//            }
//        }
//        return cTemplate;
//    }
    /**
     * @return the templateId
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * @param templateId the templateId to set
     */
    public void setTemplateId(final String templateId) {
        this.templateId = templateId;
    }

    /**
     * @return the template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(final String template) {
        this.template = template;
    }
}
