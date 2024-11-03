package com.lawencon.jobportal.model.response;

import javax.print.DocFlavor.STRING;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponse {
    private String id;
    private String userId;
    private String title;
    private String massage;
    private Boolean isRead;
    private String statusVacancyTrxId;
    private Long version;

}
