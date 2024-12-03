package com.lawencon.jobportal.model.request;

import java.time.LocalDate;
import com.lawencon.jobportal.persistent.entity.Application;
import lombok.Data;

@Data
public class ApplicationTrxRequest {
    private Application application;
    private String statusApplicationId;
    private String stageId;
    private LocalDate date;
    private Integer score;
}