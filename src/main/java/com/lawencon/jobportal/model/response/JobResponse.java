package com.lawencon.jobportal.model.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobResponse {
    private MasterResponse jobTitle;
    private List<JobDescriptionResponse> jobDescList;
    private List<JobSpecificationResponse> jobSpecList;
}
