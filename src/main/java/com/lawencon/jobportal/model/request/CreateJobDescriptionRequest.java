package com.lawencon.jobportal.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateJobDescriptionRequest {
    private String jobTitleId;
    private String titleDesc;
    private String description;
}
