package com.lawencon.jobportal.model.response;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationResponse {
    private String id;
    private String vacancyTrxId;
    private String candidateId;
    private String vacancyName;
    private String applicantName;
    private String stage;
    private String status;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
