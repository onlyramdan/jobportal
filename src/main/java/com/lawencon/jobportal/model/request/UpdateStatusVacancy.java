package com.lawencon.jobportal.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateStatusVacancy {
    private String statusId;
    private String vacancyId;
}
