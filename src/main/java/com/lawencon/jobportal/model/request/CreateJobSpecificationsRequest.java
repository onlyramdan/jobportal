package com.lawencon.jobportal.model.request;

import java.util.List;
import lombok.Data;

@Data
public class CreateJobSpecificationsRequest {
    private String jobTitleId;
    private List<String> specifications;
}
