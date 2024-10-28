package com.lawencon.jobportal.model.response;

import lombok.Data;
import java.util.List;

@Data
public class JobSpecificationsResponse {
    private String jobTitleId;
    private List<String> specifications;
}
