package com.lawencon.jobportal.model.response;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VacancyResponse {
    private String id;
    private String code;
    private String picHrId;
    private String jobTitleId;
    private String jobTitle;
    private String locationId;
    private String location;
    private String employeeTypeId;
    private String employeeType;
    private String experienceLevelId;
    private String experienceLevel;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String applicationDeadline;
    private String overview;
    private ZonedDateTime createdAt;
    private String createdBy;
    private ZonedDateTime updatedAt;
    private String updatedBy;
    private Boolean isActive;
    private Long version;
    private String Status;
}