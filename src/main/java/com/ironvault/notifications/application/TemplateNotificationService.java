package com.ironvault.notifications.application;

import com.ironvault.notifications.domain.enums.EmailTemplate;
import com.ironvault.notifications.domain.model.EmailSenderMessage;
import com.ironvault.notifications.domain.model.NotificationEvent;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TemplateNotificationService {

    public EmailSenderMessage build(NotificationEvent event) {
        return switch (event.getType()) {
            case USER_REGISTERED -> buildWelcome(event.getPayload());
            case PAYMENT_APPROVED -> buildPaymentApproved(event.getPayload());
            case PAYMENT_FAILED -> buildPaymentFailed(event.getPayload());
            case PIX_GENERATED -> buildPixGenerated(event.getPayload());
            default -> throw new IllegalArgumentException(
                    "No template found for event type: " + event.getType()
            );
        };
    }

    private EmailSenderMessage buildPixGenerated(Map<String, String> payload) {
        return new EmailSenderMessage(
                payload.get("email"),
                "Seu PIX foi gerado!",
                EmailTemplate.PIX_GENERATED,
                Map.of(
                        "pixCopyPaste", payload.getOrDefault("pixCopyPaste", ""),
                        "amount", payload.getOrDefault("amount", "")
                )
        );
    }

    private EmailSenderMessage buildPaymentFailed(Map<String, String> payload) {
        return new EmailSenderMessage(
                payload.get("email"),
                "Pagamento não processado",
                EmailTemplate.PAYMENT_FAILED,
                Map.of(
                        "amount", payload.getOrDefault("amount", ""),
                        "reason", payload.getOrDefault("reason", "")
                )
        );
    }

    private EmailSenderMessage buildPaymentApproved(Map<String, String> payload) {
        return new EmailSenderMessage(
                payload.get("email"),
                "Pagamento aprovado!",
                EmailTemplate.PAYMENT_APPROVED,
                Map.of(
                        "amount", payload.getOrDefault("amount", ""),
                        "currency", payload.getOrDefault("currency", "")
                )
        );
    }

    private EmailSenderMessage buildWelcome(Map<String, String> payload) {
        return new EmailSenderMessage(
                payload.get("email"),
                "Bem-vindo ao IronVault Payments!",
                EmailTemplate.WELCOME,
                Map.of("name", payload.getOrDefault("name", ""))
        );
    }
}
