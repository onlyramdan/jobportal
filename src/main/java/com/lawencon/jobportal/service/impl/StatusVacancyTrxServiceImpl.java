package com.lawencon.jobportal.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.CreateVacancyTrxRequest;
import com.lawencon.jobportal.persistent.entity.StatusVacancyTrx;
import com.lawencon.jobportal.persistent.repository.StatusVacancyTrxRepository;
import com.lawencon.jobportal.service.StatusVacancyTrxService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
@Transactional
public class StatusVacancyTrxServiceImpl implements StatusVacancyTrxService {
    private final StatusVacancyTrxRepository statusVacancyTrxRepository;

    @Override
    public List<StatusVacancyTrx> findAllByVacancyId(String id) {
        List<StatusVacancyTrx> transactions = statusVacancyTrxRepository.findByVacancyIdOrderByCreatedAtDesc(id);
        if (transactions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No transactions found for this vacancy ID");
        }
        return transactions;
    }

    @Override
    public StatusVacancyTrx findByVacancyId(String id) {
        return statusVacancyTrxRepository.findFirstByVacancyIdOrderByCreatedAtDesc(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found for this vacancy ID"));
    }

    @Override
    public StatusVacancyTrx save(CreateVacancyTrxRequest request) {
        StatusVacancyTrx statusVacancyTrx = new StatusVacancyTrx();
        statusVacancyTrx.setVacancy(request.getVacancy());
        statusVacancyTrx.setStatus(request.getStatusVacancy());
        statusVacancyTrx.setDate(LocalDate.now());
        statusVacancyTrx.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        statusVacancyTrx.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        statusVacancyTrx.setCreatedBy("system");
        statusVacancyTrx.setUpdatedBy("system");
        return statusVacancyTrxRepository.save(statusVacancyTrx);
    }

    @Override
    public StatusVacancyTrx update(String id, CreateVacancyTrxRequest request) {
        StatusVacancyTrx existingTransaction = statusVacancyTrxRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction not found"));

        existingTransaction.setVacancy(request.getVacancy());
        existingTransaction.setStatus(request.getStatusVacancy());
        return statusVacancyTrxRepository.save(existingTransaction);
    }

    @Override
    public void delete(String id) {
        if (!statusVacancyTrxRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction not found");
        }
        statusVacancyTrxRepository.deleteById(id);
    }
}
