package com.lawencon.jobportal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.response.NotificationResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.NotificationService;

import lombok.AllArgsConstructor;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{id}/user")
    public ResponseEntity<WebResponse<List<NotificationResponse>>> getAllNotification(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(notificationService.getAllNotification(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WebResponse<String>> updateNotification(@PathVariable String id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok(ResponseHelper.ok(
                "Notification has been marked as read"));
    }
}
