package com.lawencon.jobportal.model.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


import java.time.ZonedDateTime;

@Getter
@Setter
public class VacancyResponse {
    private String id;
    private String code;
    private String picHrId;
    private String jobTitleId;
    private String locationId;
    private String employeeTypeId;
    private String experienceLevelId;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private Date applicationDeadline;
    private String overview;
    private String Status;
    private ZonedDateTime createdAt;
    private String createdBy;
    private ZonedDateTime updatedAt;
    private String updatedBy;
    private Boolean isActive;
}