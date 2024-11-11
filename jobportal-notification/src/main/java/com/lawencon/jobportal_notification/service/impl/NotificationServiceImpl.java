package com.lawencon.jobportal_notification.service.impl;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal_notification.config.RabbitMQConfig;
import com.lawencon.jobportal_notification.model.request.NotificationRequest;
import com.lawencon.jobportal_notification.persistent.entity.Notification;
import com.lawencon.jobportal_notification.persistent.entity.User;
import com.lawencon.jobportal_notification.persistent.repository.NotificationRepository;
import com.lawencon.jobportal_notification.service.NotificationService;
import com.lawencon.jobportal_notification.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements  NotificationService {
    private final NotificationRepository  notificationRepository;
    private final UserService  userService;

    @RabbitListener(queues = RabbitMQConfig.VACANCY_NOTIFICATION_QUEUE)
    public void receivedNotification(NotificationRequest request) {
        try {
            List<User> users = userService.findAllCandidate();
            for (User user : users) {
                Notification userNotification = new Notification();
                userNotification.setUser(user);
                userNotification.setStatusVacancyTrxId(request.getStatusVacancyTrxId());
                userNotification.setTitle(request.getTitle());
                userNotification.setMessage(request.getMessage());
                userNotification.setIsRead(false);
                userNotification.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
                userNotification.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
                userNotification.setCreatedBy("system");
                userNotification.setUpdatedBy("system");
                userNotification.setIsActive(true);
                notificationRepository.save(userNotification);
            }
        } catch (Exception e) {
            System.err.println("Error processing notification: " + e.getMessage());
        }
    }
}
