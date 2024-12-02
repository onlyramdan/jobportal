package com.lawencon.jobportal.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UpdateDecscriptionRequest {
    private String id;
    private String titleDesc;
    private String description;
}
