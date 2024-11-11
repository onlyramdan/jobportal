package com.lawencon.jobportal_notification.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponse {
    private String id;
    private String statusVacancyTrxId;
    private String title;
    private String message;
    private Boolean isRead;

}
