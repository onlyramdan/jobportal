package com.lawencon.jobportal.model.response;
import java.util.List;

import lombok.Data;

@Data
public class VacancyDetailResponse {
    private VacancyResponse vacancyResponse;
    private List<JobSpecificationResponse> specifications;
    private List<JobDescriptionResponse> jobDescriptions;
}
