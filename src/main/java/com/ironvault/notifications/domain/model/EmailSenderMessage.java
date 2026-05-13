package com.ironvault.notifications.domain.model;

import com.ironvault.notifications.domain.enums.EmailTemplate;

import java.util.Map;

public class EmailSenderMessage {

    private String to;
    private String subject;
    private EmailTemplate template;
    private Map<String, String> variables;
    private String htmlContent;

    public EmailSenderMessage() { }

    public EmailSenderMessage(String to, String subject,
                              EmailTemplate template,
                              Map<String, String> variables, String htmlContent) {
        this.to = to;
        this.subject = subject;
        this.template = template;
        this.variables = variables;
        this.htmlContent = htmlContent;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public EmailTemplate getTemplate() {
        return template;
    }

    public void setTemplate(EmailTemplate template) {
        this.template = template;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
