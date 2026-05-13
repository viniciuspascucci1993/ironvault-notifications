package com.ironvault.notifications.adapter.out.entity;

import com.ironvault.notifications.domain.enums.NotificationEventType;
import com.ironvault.notifications.domain.enums.NotificationStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "notification_events")
public class NotificationEventEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private NotificationEventType type;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private String sourceService;

    @ElementCollection
    @CollectionTable(name = "notification_event_payload",
            joinColumns = @JoinColumn(name = "event_id"))
    @MapKeyColumn(name = "payload_key")
    @Column(name = "payload_value")
    private Map<String, String> payload;

    private Instant createdAt;
    private Instant processedAt;

    public NotificationEventEntity() { }

    public NotificationEventEntity(UUID id, NotificationEventType type,
                                   NotificationStatus status,
                                   String sourceService, Map<String, String> payload,
                                   Instant createdAt, Instant processedAt) {
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
