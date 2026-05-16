package com.ironvault.notifications.adapter.out.mapper;

import com.ironvault.notifications.adapter.out.entity.NotificationEventEntity;
import com.ironvault.notifications.domain.model.NotificationEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationEventMapper {
    NotificationEvent toDomain(NotificationEventEntity entity);
    NotificationEventEntity toEntity(NotificationEvent domain);
}
