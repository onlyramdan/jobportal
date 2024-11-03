package com.lawencon.jobportal.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lawencon.jobportal.persistent.entity.Notification;

import java.util.List;

public interface NotificationRespository extends JpaRepository<Notification, String> {
    List<Notification> findByUserId(String userId);
}
