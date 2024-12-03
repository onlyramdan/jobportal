package com.lawencon.jobportal.service.impl;

import com.lawencon.jobportal.model.request.UpadateStageRequest;
import com.lawencon.jobportal.model.response.ApplicationTrxResponse;
import com.lawencon.jobportal.persistent.entity.ApplicationTrx;
import com.lawencon.jobportal.persistent.repository.AplicationTrxRepository;
import com.lawencon.jobportal.service.ApplicationTrxService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationTrxServiceImpl implements ApplicationTrxService {
    private final AplicationTrxRepository applicationTrxRepository;

    @Override
    public ApplicationTrxResponse createApplicationTrx(ApplicationTrx request) {
        ApplicationTrx saved = applicationTrxRepository.saveAndFlush(request);
        return convertToTrxResponse(saved);
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
        response.setStageName(applicationTrx.getStage() != null ? applicationTrx.getStage().getName() : null);
        response.setStatusApplicationName(
                applicationTrx.getStatusApplication() != null ? applicationTrx.getStatusApplication().getName() : null);
        response.setDate(applicationTrx.getDate());
        response.setScore(applicationTrx.getScore());
        return response;
    }

    @Override
    public ApplicationTrxResponse updateAplicationTrx(String id, UpadateStageRequest request) {
        ApplicationTrx saved = applicationTrxRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application Transaction Not Found"));
        BeanUtils.copyProperties(request, saved, "id");
        saved = applicationTrxRepository.saveAndFlush(saved);
        return convertToTrxResponse(saved);
    }

    @Override
    public ApplicationTrx findEntityById(String id) {
        return applicationTrxRepository.findById(id).orElseThrow(()->
         new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Transaction Not Found"));
    }
}
