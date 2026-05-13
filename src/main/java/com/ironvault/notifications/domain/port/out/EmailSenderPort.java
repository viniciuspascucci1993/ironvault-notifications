package com.ironvault.notifications.domain.port.out;

import com.ironvault.notifications.domain.model.EmailSenderMessage;

public interface EmailSenderPort {
    void send(EmailSenderMessage message);
}
