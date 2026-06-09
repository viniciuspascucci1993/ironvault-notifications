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

@Service
@Slf4j
public class TemplateNotificationService {

    public EmailSenderMessage build(NotificationEvent event) {
        return switch (event.getType()) {
            case USER_REGISTERED -> buildWelcome(event.getPayload());
            case PAYMENT_APPROVED -> buildPaymentApproved(event.getPayload());
            case PAYMENT_FAILED -> buildPaymentFailed(event.getPayload());
            case PIX_GENERATED -> buildPixGenerated(event.getPayload());
            case PASSWORD_RESET -> buildPasswordReset(event.getPayload());
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

    private EmailSenderMessage buildPasswordReset(Map<String, String> payload) {
        String resetLink = payload.getOrDefault("resetLink", "");
        String html = loadTemplate("templates/email/password-reset.html")
                .replace("{{resetLink}}", resetLink);

        return new EmailSenderMessage(
                payload.get("email"),
                "Redefinição de senha - IronVault Payments",
                EmailTemplate.PASSWORD_RESET,
                Map.of("resetLink", resetLink),
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
}
