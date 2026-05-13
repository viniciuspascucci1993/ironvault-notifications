package com.ironvault.notifications.domain.port.out;

import com.ironvault.notifications.domain.model.NotificationEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationEventRepositoryPort {
    NotificationEvent save(NotificationEvent event);
    Optional<NotificationEvent> findById(UUID id);
    List<NotificationEvent> findPendingEvents();
}
