package com.ironvault.notifications.domain.model;

import com.ironvault.notifications.domain.enums.NotificationChannel;
import com.ironvault.notifications.domain.enums.NotificationStatus;

import java.time.Instant;
import java.util.UUID;

public class NotificationLog {

    private UUID id;
    private UUID eventId;
    private NotificationChannel channel;
    private NotificationStatus status;
    private String recipient;
    private String errorMessage;
    private Instant createdAt;
    private Instant sentAt;

    public NotificationLog() { }

    public NotificationLog(UUID id, UUID eventId,
                           NotificationChannel channel,
                           NotificationStatus status, String recipient,
                           String errorMessage, Instant createdAt,
                           Instant sentAt) {
        this.id = id;
        this.eventId = eventId;
        this.channel = channel;
        this.status = status;
        this.recipient = recipient;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
        this.sentAt = sentAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public void setSentAt(Instant sentAt) {
        this.sentAt = sentAt;
    }
}
