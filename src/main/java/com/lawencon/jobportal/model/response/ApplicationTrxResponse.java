package com.lawencon.jobportal.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ApplicationTrxResponse {
    private String id;
    private String stageName;
    private String statusApplicationName;
    private LocalDate date;
    private Integer score;
}
