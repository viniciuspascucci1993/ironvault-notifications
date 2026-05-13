package com.ironvault.notifications.adapter.out.email;

import com.ironvault.notifications.domain.model.EmailSenderMessage;
import com.ironvault.notifications.domain.port.out.EmailSenderPort;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResendEmailSenderTestingAdapter implements EmailSenderPort {

    private final Resend resend;

    @Value("${app.resend.from}")
    private String from;

    public ResendEmailSenderTestingAdapter(@Value("${app.resend.api-key}") String apiKey) {
        this.resend = new Resend(apiKey);
    }

    @Override
    public void send(EmailSenderMessage message) {
        try {
            CreateEmailOptions options = CreateEmailOptions.builder()
                    .from(from)
                    .to(message.getTo())
                    .subject(message.getSubject())
                    .html(message.getHtmlContent())
                    .build();

            CreateEmailResponse response = resend.emails().send(options);

            log.info("Email sent via Resend id={} to={}", response.getId(), message.getTo());

        } catch (ResendException ex) {
            log.error("Failed to send email via Resend to={} reason={}", message.getTo(), ex.getMessage());
            throw new RuntimeException("Failed to send email: " + ex.getMessage(), ex);
        }
    }
}
