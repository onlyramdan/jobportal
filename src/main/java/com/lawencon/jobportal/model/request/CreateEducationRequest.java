package com.lawencon.jobportal.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEducationRequest {
    private String profileId;
    private String educationLevelId;
    private String name;
    private Double gpa;
    private Integer startYear;
    private Integer endYear;
    private String major;
}
