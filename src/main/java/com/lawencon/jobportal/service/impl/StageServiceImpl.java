package com.lawencon.jobportal.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.MasterRequest;
import com.lawencon.jobportal.model.request.UpdateMasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.persistent.entity.Stage;
import com.lawencon.jobportal.persistent.repository.StageRepostiory;
import com.lawencon.jobportal.service.StageService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;
import java.time.ZoneOffset;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class StageServiceImpl implements  StageService {
    private final StageRepostiory stageRepostiory;   
        
    private MasterResponse converterResponse(Stage  stage) {
        MasterResponse masterResponse = new MasterResponse();
        BeanUtils.copyProperties(stage, masterResponse);
        return masterResponse;
    }

    @Override
    public void delete(String id) {
        stageRepostiory.deleteById(id);
    }

    @Override
    public List<MasterResponse> findAll() {
        return stageRepostiory.findAll().stream().map(stage->
        converterResponse(stage)).collect(Collectors.toList());
    }

    @Override
    public MasterResponse findById(String id) {
        return converterResponse(findEntityById(id));

    }

    @Override
    public Stage findEntityById(String id) {
        return stageRepostiory.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stage Not Found"));
    }

    @Override
    public MasterResponse save(MasterRequest request) {
        Stage stage = new Stage();
        BeanUtils.copyProperties(request, stage);
        stage.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        stage.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        stage.setCreatedBy("system");
        stage.setUpdatedBy("system");
        stage.setIsActive(true);
        return converterResponse(stageRepostiory.save(stage));
    }

    @Override
    public MasterResponse update(UpdateMasterRequest request) {
        Stage stage = findEntityById(request.getId());
        BeanUtils.copyProperties(request, stage);
        return converterResponse(stageRepostiory.save(stage)); 
    }

    @Override
    public Stage findByCode(String code) {
        return stageRepostiory.findByCode(code).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stage Not Found"));
    }
}
