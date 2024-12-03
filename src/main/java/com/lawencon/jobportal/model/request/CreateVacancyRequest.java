package com.lawencon.jobportal.model.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class CreateVacancyRequest {
    private String picHrId;
    private String jobTitleId;
    private String locationId;
    private String employeeTypeId;
    private String experienceLevelId;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private LocalDate applicationDeadline;
    private String overview;
}
