package com.lawencon.jobportal.model.request;

import com.lawencon.jobportal.persistent.entity.StatusVacancy;
import com.lawencon.jobportal.persistent.entity.Vacancy;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateVacancyTrxRequest {
    private Vacancy vacancy;
    private StatusVacancy statusVacancy;
}
