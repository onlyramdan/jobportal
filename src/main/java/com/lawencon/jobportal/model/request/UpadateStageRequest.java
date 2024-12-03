package com.lawencon.jobportal.model.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UpadateStageRequest {
    private String statusApplicationId;
    private LocalDate date;
    private Integer score;   
}
