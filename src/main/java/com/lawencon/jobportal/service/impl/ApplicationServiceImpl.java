package com.lawencon.jobportal.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.ApplicationRequest;
import com.lawencon.jobportal.model.request.UpadateStageRequest;
import com.lawencon.jobportal.model.response.ApplicationResponse;
import com.lawencon.jobportal.model.response.ApplicationResponseDetail;
import com.lawencon.jobportal.model.response.ApplicationTrxResponse;
import com.lawencon.jobportal.persistent.entity.Application;
import com.lawencon.jobportal.persistent.entity.ApplicationTrx;
import com.lawencon.jobportal.persistent.entity.Profile;
import com.lawencon.jobportal.persistent.entity.StatusVacancyTrx;
import com.lawencon.jobportal.persistent.repository.AplicationRepository;
import com.lawencon.jobportal.service.ApplicationService;
import com.lawencon.jobportal.service.ApplicationTrxService;
import com.lawencon.jobportal.service.ProfileService;
import com.lawencon.jobportal.service.StageService;
import com.lawencon.jobportal.service.StatusVacancyTrxService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final AplicationRepository applicationRepository;
    private final StatusVacancyTrxService statusVacancyTrxService;
    private final ApplicationTrxService applicationTrxService;
    private final StageService stageService;
    private final ProfileService profileService;

    private ApplicationResponseDetail convertToResponse(Application application) {
        ApplicationResponseDetail response = new ApplicationResponseDetail();
        BeanUtils.copyProperties(application, response);
        response.setId(application.getId());
        response.setVacancyTrxId(application.getStatusVacancyTrx().getId());
        response.setCandidateId(application.getCandidate().getId());
        response.setApplicantName(application.getCandidate().getFullName());
        response.setVacancyName(application.getStatusVacancyTrx().getVacancy().getJobTitle().getName());
        List<ApplicationTrxResponse> applicationTrxResponses = applicationTrxService
                .getAllTransactionsByApplicationId(application.getId());
        response.setTransactions(applicationTrxResponses);
        return response;
    }

    @Override
    public void createApplication(ApplicationRequest request) {
        StatusVacancyTrx vacancyTrx = statusVacancyTrxService.findEntityById(request.getVacancyTrxId());
        Profile profile = profileService.findEntityById(request.getCandidateId());
        Application application = new Application();
        application.setStatusVacancyTrx(vacancyTrx);
        application.setCandidate(profile);

        /*
         * Pelamar applied untuk lowongan yang sedang dibuka
         * Pelamar minimal ada CV
         */

        if (!vacancyTrx.getStatus().getCode().equals("ON")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vacancy Not Ongoing");
        }

        Application applied = applicationRepository.saveAndFlush(application);
        ApplicationTrx applicationTrx = new ApplicationTrx();
        applicationTrx.setApplication(applied);
        applicationTrx.setStage(stageService.findByCode("AP"));
        applicationTrxService.createApplicationTrx(applicationTrx);
    }

    @Override
    public ApplicationResponseDetail getApplicationById(String id) {
        Application application = applicationRepository.findById(id).orElse(null);
        return convertToResponse(application);
    }

    @Override
    public List<ApplicationResponse> getApllicationByCandidateId(String profileId) {
        List<Application> applications = applicationRepository.findByCandidateId(profileId);
        return applications.stream()
                .map(application -> {
                    ApplicationResponse response = new ApplicationResponse();
                    BeanUtils.copyProperties(application, response);
                    response.setId(application.getId());
                    response.setVacancyTrxId(application.getStatusVacancyTrx().getId());
                    response.setCandidateId(application.getCandidate().getId());
                    response.setApplicantName(application.getCandidate().getFullName());
                    response.setVacancyName(application.getStatusVacancyTrx().getVacancy().getJobTitle().getName());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationTrxResponse updateAplicationStage(String aplicationTrxId, UpadateStageRequest request) {
        return  applicationTrxService.updateAplicationTrx(aplicationTrxId, request);
    }

    // @Override
    // public ApplicationResponse upgradeAplicationStage(UpgradeAplicationStageRequest request) {
    //     Stage stage = stageService.findEntityById(request.getStageId());
    //     // Application application =  applicationRepository.findById(request.getAppliacationId())


    // }
}
