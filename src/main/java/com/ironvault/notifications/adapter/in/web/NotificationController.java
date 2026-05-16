package com.ironvault.notifications.adapter.in.web;

import com.ironvault.notifications.adapter.in.dto.NotificationEventRequest;
import com.ironvault.notifications.domain.model.NotificationEvent;
import com.ironvault.notifications.domain.port.in.ProcessNotificationEventUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final ProcessNotificationEventUseCase processNotificationEventUseCase;

    public NotificationController(ProcessNotificationEventUseCase processNotificationEventUseCase) {
        this.processNotificationEventUseCase = processNotificationEventUseCase;
    }

    @PostMapping("/events")
    public ResponseEntity<Map<String, Object>> receiveEvent(
            @Valid @RequestBody NotificationEventRequest request) {

        NotificationEvent event = new NotificationEvent();
        event.setType(request.getType());
        event.setSourceService(request.getSourceService());
        event.setPayload(request.getPayload());

        processNotificationEventUseCase.process(event);

        return ResponseEntity.accepted().body(Map.of(
                "received", true,
                "type", request.getType()
        ));
    }
}
