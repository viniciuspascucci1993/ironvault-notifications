package com.ironvault.notifications.adapter.out.repository;

import com.ironvault.notifications.adapter.out.entity.NotificationLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationLogJpaRepository extends JpaRepository<NotificationLogEntity, UUID> {
    List<NotificationLogEntity> findByEventId(UUID eventId);
}
