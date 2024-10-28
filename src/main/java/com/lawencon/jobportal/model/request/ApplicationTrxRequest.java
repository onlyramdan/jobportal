package com.lawencon.jobportal.model.request;
import java.time.LocalDate;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApplicationTrxRequest {
    private String applicationId;
    private String statusApplicationId;
    private String stageId;
    private LocalDate date;
    private BigDecimal score;
}