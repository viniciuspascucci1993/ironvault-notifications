package com.ironvault.notifications.application;

import com.ironvault.notifications.domain.enums.NotificationChannel;
import com.ironvault.notifications.domain.enums.NotificationStatus;
import com.ironvault.notifications.domain.model.NotificationEvent;
import com.ironvault.notifications.domain.model.NotificationLog;
import com.ironvault.notifications.domain.port.out.EmailSenderPort;
import com.ironvault.notifications.domain.port.out.NotificationEventRepositoryPort;
import com.ironvault.notifications.domain.port.out.NotificationLogRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class EmailDispatcherService {

    private final TemplateNotificationService templateNotificationService;
    private final NotificationEventRepositoryPort eventRepositoryPort;
    private final NotificationLogRepositoryPort logRepositoryPort;
    private final EmailSenderPort emailSenderPort;

    public EmailDispatcherService(TemplateNotificationService templateNotificationService,
                                  NotificationEventRepositoryPort eventRepositoryPort,
                                  NotificationLogRepositoryPort logRepositoryPort,
                                  EmailSenderPort emailSenderPort) {
        this.templateNotificationService = templateNotificationService;
        this.eventRepositoryPort = eventRepositoryPort;
        this.logRepositoryPort = logRepositoryPort;
        this.emailSenderPort = emailSenderPort;
    }

    public void dispatch(NotificationEvent event) {

        NotificationLog notificationLog  = new NotificationLog(
                UUID.randomUUID(),
                event.getId(),
                NotificationChannel.EMAIL,
                NotificationStatus.PENDING,
                event.getPayload().get("email"),
                null,
                Instant.now(),
                null
        );

        try {
            var emailMessage = templateNotificationService.build(event);
            emailSenderPort.send(emailMessage);

            notificationLog .setStatus(NotificationStatus.SENT);
            notificationLog .setSentAt(Instant.now());

            event.setStatus(NotificationStatus.SENT);
            event.setProcessedAt(Instant.now());
            eventRepositoryPort.save(event);

            logRepositoryPort.save(notificationLog);

            log.info("Email dispatched eventId={} type={} recipient={}",
                    event.getId(), event.getType(), notificationLog .getRecipient());

        } catch (Exception ex) {
            notificationLog .setStatus(NotificationStatus.FAILED);
            notificationLog .setErrorMessage(ex.getMessage());
            logRepositoryPort.save(notificationLog );

            event.setStatus(NotificationStatus.FAILED);
            event.setProcessedAt(Instant.now());
            eventRepositoryPort.save(event);

            log.error("Email dispatch failed eventId={} reason={}",
                    event.getId(), ex.getMessage());
        }

    }
}
