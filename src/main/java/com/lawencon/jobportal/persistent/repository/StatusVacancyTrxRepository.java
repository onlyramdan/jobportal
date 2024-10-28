package com.lawencon.jobportal.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistent.entity.StatusVacancyTrx;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusVacancyTrxRepository extends JpaRepository<StatusVacancyTrx, String> {
    List<StatusVacancyTrx> findByVacancyIdOrderByCreatedAtDesc(String vacancyId);
    Optional<StatusVacancyTrx> findFirstByVacancyIdOrderByCreatedAtDesc(String vacancyId);
}
