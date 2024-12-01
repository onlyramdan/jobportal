package com.lawencon.jobportal.model.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateJobRequest {
    private String jobTitle;
    private List<JobDescRequest> jobDesc;
    private List<String> jobSpec;
}
