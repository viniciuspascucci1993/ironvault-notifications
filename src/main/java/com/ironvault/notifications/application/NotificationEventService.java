package com.ironvault.notifications.application;

import com.ironvault.notifications.domain.enums.NotificationStatus;
import com.ironvault.notifications.domain.model.NotificationEvent;
import com.ironvault.notifications.domain.port.in.ProcessNotificationEventUseCase;
import com.ironvault.notifications.domain.port.out.NotificationEventRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class NotificationEventService implements ProcessNotificationEventUseCase {

    private final NotificationEventRepositoryPort eventRepositoryPort;
    private final EmailDispatcherService emailDispatcherService;

    public NotificationEventService(NotificationEventRepositoryPort eventRepositoryPort,
                                    EmailDispatcherService emailDispatcherService) {
        this.eventRepositoryPort = eventRepositoryPort;
        this.emailDispatcherService = emailDispatcherService;
    }

    @Override
    public void process(NotificationEvent event) {
        event.setId(UUID.randomUUID());
        event.setStatus(NotificationStatus.PENDING);
        event.setCreatedAt(Instant.now());

        NotificationEvent saved = eventRepositoryPort.save(event);

        log.info("Notification event received type={} sourceService={}",
                event.getType(), event.getSourceService());

        emailDispatcherService.dispatch(saved);
    }
}
