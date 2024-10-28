package com.lawencon.jobportal.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExperienceResponse {
    private String id;
    private String company;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String experienceLevel;
}
