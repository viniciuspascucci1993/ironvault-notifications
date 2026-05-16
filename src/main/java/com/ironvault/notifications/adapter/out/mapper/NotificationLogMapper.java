package com.ironvault.notifications.adapter.out.mapper;

import com.ironvault.notifications.adapter.out.entity.NotificationLogEntity;
import com.ironvault.notifications.domain.model.NotificationLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationLogMapper {
    NotificationLog toDomain(NotificationLogEntity entity);
    NotificationLogEntity toEntity(NotificationLog domain);
}
