package com.lawencon.jobportal.model.response;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EducationResponse {
    private String id;
    private String profileId;
    private String education;
    private String name;
    private Double gpa;
    private Integer startYear;
    private Integer endYear;
    private String major;
    private ZonedDateTime createdAt;
    private String createdBy;
    private ZonedDateTime updatedAt;
    private String updatedBy;
    private Boolean isActive;
}
