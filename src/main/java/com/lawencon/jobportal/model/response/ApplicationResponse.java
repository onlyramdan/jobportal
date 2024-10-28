package com.lawencon.jobportal.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ApplicationResponse {
    private String id;
    private String vacancyTrxId;
    private String candidateId;
    private String createdBy;
    private LocalDate createdAt;
    private List<ApplicationTrxResponse> transactions;
}
