package com.lawencon.jobportal.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.response.NotificationResponse;
import com.lawencon.jobportal.persistent.entity.Notification;
import com.lawencon.jobportal.persistent.repository.NotificationRespository;
import com.lawencon.jobportal.service.NotificationService;

import lombok.AllArgsConstructor;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements  NotificationService {
    private final NotificationRespository notificationRespository;

    @Override
    public List<NotificationResponse> getAllNotification(String userId) {
        return notificationRespository.findByUserId(userId).stream()
        .map(notification -> NotificationResponse.builder()
            .id(notification.getId())
            .title(notification.getTitle())
            .massage(notification.getMessage())
            .isRead(notification.getIsRead())
            .statusVacancyTrxId(notification.getStatusVacancyTrxId().getId())
            .userId(notification.getUser().getId())
            .version(notification.getVersion())
            .build()
        )
        .collect(Collectors.toList());
    }

    @Override
    public void markAsRead(String notificationId) {
        Optional<Notification> notificationOptional = notificationRespository.findById(notificationId);
        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            notification.setIsRead(true);
            notificationRespository.saveAndFlush(notification);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Notification not found");
        }
    }


}
