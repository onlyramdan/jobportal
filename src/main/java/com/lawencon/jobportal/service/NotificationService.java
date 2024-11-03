package com.lawencon.jobportal.service;

import java.util.List;

import com.lawencon.jobportal.model.response.NotificationResponse;

public interface NotificationService {
    List<NotificationResponse> getAllNotification(String userId);
    void markAsRead(String notificationId);
}
