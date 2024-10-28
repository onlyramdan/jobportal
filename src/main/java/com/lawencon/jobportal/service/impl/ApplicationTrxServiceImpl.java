package com.lawencon.jobportal.service.impl;

import com.lawencon.jobportal.model.request.ApplicationTrxRequest;
import com.lawencon.jobportal.model.response.ApplicationTrxResponse;
import com.lawencon.jobportal.persistent.entity.Application;
import com.lawencon.jobportal.persistent.entity.ApplicationTrx;
import com.lawencon.jobportal.persistent.entity.Stage;
import com.lawencon.jobportal.persistent.entity.StatusAplication;
import com.lawencon.jobportal.persistent.repository.AplicationRepository;
import com.lawencon.jobportal.persistent.repository.AplicationTrxRepository;
import com.lawencon.jobportal.persistent.repository.StageRepostiory;
import com.lawencon.jobportal.persistent.repository.StatusAplicationRepository;
import com.lawencon.jobportal.service.ApplicationTrxService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationTrxServiceImpl implements ApplicationTrxService {

    private final AplicationRepository applicationRepository;
    private final StageRepostiory stageRepository;
    private final StatusAplicationRepository statusApplicationRepository;
    private final AplicationTrxRepository applicationTrxRepository;

    @Override
    @Transactional
    public ApplicationTrxResponse createApplicationTrx(ApplicationTrxRequest request) {
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        Stage stage = stageRepository.findById(request.getStageId())
                .orElseThrow(() -> new RuntimeException("Stage not found"));

        StatusAplication statusApplication = statusApplicationRepository.findById(request.getStatusApplicationId())
                .orElseThrow(() -> new RuntimeException("Status Application not found"));

        ApplicationTrx applicationTrx = new ApplicationTrx();
        applicationTrx.setApplication(application);
        applicationTrx.setStage(stage);
        applicationTrx.setStatusApplication(statusApplication);
        applicationTrx.setDate(request.getDate());
        applicationTrx.setScore(request.getScore());
        applicationTrxRepository.save(applicationTrx);
        return convertToTrxResponse(applicationTrx);
    }

    @Override
    public List<ApplicationTrxResponse> getAllTransactionsByApplicationId(String applicationId) {
        List<ApplicationTrx> transactions = applicationTrxRepository.findByApplicationId(applicationId);
        return transactions.stream()
                .map(this::convertToTrxResponse)
                .collect(Collectors.toList());
    }

    private ApplicationTrxResponse convertToTrxResponse(ApplicationTrx applicationTrx) {
        ApplicationTrxResponse response = new ApplicationTrxResponse();
        response.setId(applicationTrx.getId());
        response.setStageName(applicationTrx.getStage().getName());
        response.setStatusApplicationName(applicationTrx.getStatusApplication().getName());
        response.setDate(applicationTrx.getDate());
        response.setScore(applicationTrx.getScore());
        return response;
    }
}
