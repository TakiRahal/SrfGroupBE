package com.takirahal.srfgroup.modules.notification.mapper;

import com.takirahal.srfgroup.mapper.EntityMapper;
import com.takirahal.srfgroup.modules.notification.dto.NotificationDTO;
import com.takirahal.srfgroup.modules.notification.entities.Notification;
import com.takirahal.srfgroup.modules.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Notification} and its DTO {@link NotificationDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {
    @Mapping(target = "user", source = "user", qualifiedByName = "email")
    NotificationDTO toDto(Notification s);
}
