package com.ironvault.notifications.adapter.in.dto;

import com.ironvault.notifications.domain.enums.NotificationEventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class NotificationEventRequest {

    @NotNull
    private NotificationEventType type;

    @NotBlank
    private String sourceService;

    @NotNull
    private Map<String, String> payload;

    public NotificationEventRequest() { }

    public NotificationEventRequest(NotificationEventType type, String sourceService, Map<String, String> payload) {
        this.type = type;
        this.sourceService = sourceService;
        this.payload = payload;
    }

    public @NotNull NotificationEventType getType() {
        return type;
    }

    public void setType(@NotNull NotificationEventType type) {
        this.type = type;
    }

    public @NotBlank String getSourceService() {
        return sourceService;
    }

    public void setSourceService(@NotBlank String sourceService) {
        this.sourceService = sourceService;
    }

    public @NotNull Map<String, String> getPayload() {
        return payload;
    }

    public void setPayload(@NotNull Map<String, String> payload) {
        this.payload = payload;
    }
}
