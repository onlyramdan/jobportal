package com.lawencon.jobportal_notification.persistent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal_notification.persistent.entity.Notification;

@Repository
public interface NotificationRepository extends  JpaRepository<Notification,String> {
    List<Notification> findByUserId(String userId);
}
