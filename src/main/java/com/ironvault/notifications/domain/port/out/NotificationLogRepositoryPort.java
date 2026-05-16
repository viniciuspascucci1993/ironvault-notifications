package com.ironvault.notifications.domain.port.out;

import com.ironvault.notifications.domain.model.NotificationLog;

import java.util.List;
import java.util.UUID;

public interface NotificationLogRepositoryPort {
    NotificationLog save(NotificationLog log);
    List<NotificationLog> findByEventId(UUID eventId);
}
