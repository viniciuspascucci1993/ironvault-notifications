package com.ironvault.notifications.adapter.out.persistence;

import com.ironvault.notifications.adapter.out.mapper.NotificationLogMapper;
import com.ironvault.notifications.adapter.out.repository.NotificationLogJpaRepository;
import com.ironvault.notifications.domain.enums.NotificationStatus;
import com.ironvault.notifications.domain.model.NotificationLog;
import com.ironvault.notifications.domain.port.out.NotificationLogRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class NotificationLogRepositoryAdapter implements NotificationLogRepositoryPort {

    private final NotificationLogJpaRepository jpaRepository;
    private final NotificationLogMapper logMapper;

    public NotificationLogRepositoryAdapter(NotificationLogJpaRepository jpaRepository,
                    NotificationLogMapper logMapper) {
        this.jpaRepository = jpaRepository;
        this.logMapper = logMapper;
    }

    @Override
    public NotificationLog save(NotificationLog log) {
        return logMapper.toDomain(jpaRepository.save(logMapper.toEntity(log)));
    }

    @Override
    public List<NotificationLog> findByEventId(UUID eventId) {
        return jpaRepository.findByEventId(eventId)
                .stream()
                .map(logMapper::toDomain)
                .toList();
    }
}
