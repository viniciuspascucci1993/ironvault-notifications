package com.ironvault.notifications.domain.model;

import com.ironvault.notifications.domain.enums.NotificationEventType;
import com.ironvault.notifications.domain.enums.NotificationStatus;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class NotificationEvent {

    private UUID id;
    private NotificationEventType type;
    private NotificationStatus status;
    private String sourceService;
    private Map<String, String> payload;
    private Instant createdAt;
    private Instant processedAt;

    public NotificationEvent() { }

    public NotificationEvent(UUID id, NotificationEventType type,
                             NotificationStatus status, String sourceService,
                             Map<String, String> payload, Instant createdAt,
                             Instant processedAt) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.sourceService = sourceService;
        this.payload = payload;
        this.createdAt = createdAt;
        this.processedAt = processedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public NotificationEventType getType() {
        return type;
    }

    public void setType(NotificationEventType type) {
        this.type = type;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public String getSourceService() {
        return sourceService;
    }

    public void setSourceService(String sourceService) {
        this.sourceService = sourceService;
    }

    public Map<String, String> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, String> payload) {
        this.payload = payload;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Instant processedAt) {
        this.processedAt = processedAt;
    }
}
