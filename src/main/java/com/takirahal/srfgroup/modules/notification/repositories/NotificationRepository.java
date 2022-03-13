package com.takirahal.srfgroup.modules.notification.repositories;

import com.takirahal.srfgroup.modules.notification.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {
    @Query("select notification from Notification notification where notification.user.username = ?#{principal.username}")
    List<Notification> findByUserIsCurrentUser();

    @Query(
            "SELECT COUNT(notification) FROM Notification notification WHERE notification.user.username = ?#{principal.username} AND notification.isRead=false"
    )
    long getNotReadNotifications();
}
