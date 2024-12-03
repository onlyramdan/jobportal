package com.lawencon.jobportal.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
public class ApplicationTrxResponse {
    private String id;
    private String stageName;
    private String statusApplicationName;
    private LocalDate date;
    private Integer score;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

}
