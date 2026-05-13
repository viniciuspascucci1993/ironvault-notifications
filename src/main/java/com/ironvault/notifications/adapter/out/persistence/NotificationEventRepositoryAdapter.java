package com.ironvault.notifications.adapter.out.persistence;

import com.ironvault.notifications.adapter.out.mapper.NotificationEventMapper;
import com.ironvault.notifications.adapter.out.repository.NotificationEventJpaRepository;
import com.ironvault.notifications.domain.enums.NotificationStatus;
import com.ironvault.notifications.domain.model.NotificationEvent;
import com.ironvault.notifications.domain.port.out.NotificationEventRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class NotificationEventRepositoryAdapter implements NotificationEventRepositoryPort {

    private final NotificationEventJpaRepository jpaRepository;
    private final NotificationEventMapper eventMapper;

    public NotificationEventRepositoryAdapter(NotificationEventJpaRepository jpaRepository,
                                              NotificationEventMapper eventMapper) {
        this.jpaRepository = jpaRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public NotificationEvent save(NotificationEvent event) {
        return eventMapper.toDomain(jpaRepository.save(eventMapper.toEntity(event)));
    }

    @Override
    public Optional<NotificationEvent> findById(UUID id) {
        return jpaRepository.findById(id).map(eventMapper::toDomain);
    }

    @Override
    public List<NotificationEvent> findPendingEvents() {
        return jpaRepository.findByStatus(NotificationStatus.PENDING)
                .stream()
                .map(eventMapper::toDomain)
                .toList();
    }
}
