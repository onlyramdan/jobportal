package com.lawencon.jobportal.model.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateExperienceRequest {
    private String company;
    private String position;
    private String profileId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String experienceLevelId;
}
