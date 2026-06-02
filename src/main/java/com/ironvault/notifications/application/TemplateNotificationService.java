package com.ironvault.notifications.application;

import com.ironvault.notifications.domain.enums.EmailTemplate;
import com.ironvault.notifications.domain.model.EmailSenderMessage;
import com.ironvault.notifications.domain.model.NotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.ironvault.notifications.domain.enums.EmailTemplate.EMAIL_CONFIRMATION;

@Service
@Slf4j
public class TemplateNotificationService {

    public EmailSenderMessage build(NotificationEvent event) {
        return switch (event.getType()) {
            case USER_REGISTERED -> buildWelcome(event.getPayload());
            case PAYMENT_APPROVED -> buildPaymentApproved(event.getPayload());
            case PAYMENT_FAILED -> buildPaymentFailed(event.getPayload());
            case PIX_GENERATED -> buildPixGenerated(event.getPayload());
            case EMAIL_CONFIRMATION -> buildEmailConfirmation(event.getPayload());
            default -> throw new IllegalArgumentException(
                    "No template found for event type: " + event.getType()
            );
        };
    }

    private EmailSenderMessage buildPaymentApproved(Map<String, String> payload) {
        String amount = payload.getOrDefault("amount", "");
        String currency = payload.getOrDefault("currency", "BRL");
        String html = loadTemplate("templates/email/payment-approved.html")
                .replace("{{amount}}", amount)
                .replace("{{currency}}", currency);

        return new EmailSenderMessage(
                payload.get("email"),
                "Pagamento aprovado!",
                EmailTemplate.PAYMENT_APPROVED,
                Map.of("amount", payload.getOrDefault("amount", ""),
                        "currency", payload.getOrDefault("currency", "")),
                html
        );
    }

    private EmailSenderMessage buildPaymentFailed(Map<String, String> payload) {
        String amount = payload.getOrDefault("amount", "");
        String reason = payload.getOrDefault("reason", "Erro técnico");
        String html = loadTemplate("templates/email/payment-failed.html")
                .replace("{{amount}}", amount)
                .replace("{{reason}}", reason);

        return new EmailSenderMessage(
                payload.get("email"),
                "Pagamento não processado",
                EmailTemplate.PAYMENT_FAILED,
                Map.of("amount", payload.getOrDefault("amount", ""),
                        "reason", payload.getOrDefault("reason", "")),
                html
        );
    }


    private EmailSenderMessage buildWelcome(Map<String, String> payload) {
        String name = payload.getOrDefault("name", "");
        String html = loadTemplate("templates/email/welcome.html")
                .replace("{{name}}", name);

        return new EmailSenderMessage(
                payload.get("email"),
                "Bem-vindo ao IronVault Payments!",
                EmailTemplate.WELCOME,
                Map.of("name", name),
                html
        );
    }

    private EmailSenderMessage buildPixGenerated(Map<String, String> payload) {
        String amount = payload.getOrDefault("amount", "");
        String pixCopyPaste = payload.getOrDefault("pixCopyPaste", "");
        String html = loadTemplate("templates/email/pix-generated.html")
                .replace("{{amount}}", amount)
                .replace("{{pixCopyPaste}}", pixCopyPaste);

        return new EmailSenderMessage(
                payload.get("email"),
                "Seu PIX foi gerado!",
                EmailTemplate.PIX_GENERATED,
                Map.of("pixCopyPaste", payload.getOrDefault("pixCopyPaste", ""),
                        "amount", payload.getOrDefault("amount", "")),
                html
        );
    }

    private String loadTemplate(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            return resource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException ex) {
            log.error("Failed to load email template path={} reason={}", path, ex.getMessage());
            throw new RuntimeException("Failed to load email template: " + path, ex);
        }
    }

    private EmailSenderMessage buildEmailConfirmation(Map<String, String> payload) {
        String confirmationLink = payload.getOrDefault("confirmationLink", "");
        String html = loadTemplate("templates/email/email-confirmation.html")
                .replace("{{confirmationLink}}", confirmationLink);

        return new EmailSenderMessage(
                payload.get("email"),
                "Confirme seu email - IronVault Payments",
                EmailTemplate.EMAIL_CONFIRMATION,
                Map.of("confirmationLink", confirmationLink),
                html
        );
    }
}
