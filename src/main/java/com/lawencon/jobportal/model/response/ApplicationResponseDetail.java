package com.lawencon.jobportal.model.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationResponseDetail extends ApplicationResponse {
    private List<ApplicationTrxResponse> transactions;
}
