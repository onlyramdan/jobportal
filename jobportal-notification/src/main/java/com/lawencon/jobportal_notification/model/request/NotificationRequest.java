package com.lawencon.jobportal_notification.model.request;

import lombok.Data;

@Data
public class NotificationRequest {
    private String title;
    private String statusVacancyTrxId;
    private String  message;
}
