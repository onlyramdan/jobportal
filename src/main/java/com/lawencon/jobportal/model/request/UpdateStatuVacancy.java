package com.lawencon.jobportal.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateStatuVacancy {
    private String statusId;
    private String vacancyId;
}
