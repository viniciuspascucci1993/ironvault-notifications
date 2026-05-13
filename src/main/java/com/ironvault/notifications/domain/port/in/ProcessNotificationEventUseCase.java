package com.ironvault.notifications.domain.port.in;

import com.ironvault.notifications.domain.model.NotificationEvent;

public interface ProcessNotificationEventUseCase {
    void process(NotificationEvent event);
}
