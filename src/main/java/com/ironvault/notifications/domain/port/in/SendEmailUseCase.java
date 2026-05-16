package com.ironvault.notifications.domain.port.in;

import com.ironvault.notifications.domain.model.EmailSenderMessage;

public interface SendEmailUseCase {
    void send(EmailSenderMessage message);
}
