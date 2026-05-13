package com.ironvault.notifications.adapter.out.repository;

import com.ironvault.notifications.adapter.out.entity.NotificationEventEntity;
import com.ironvault.notifications.domain.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationEventJpaRepository extends JpaRepository<NotificationEventEntity, UUID> {
    List<NotificationEventEntity> findByStatus(NotificationStatus status);
}
