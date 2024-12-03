package com.lawencon.jobportal.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lawencon.jobportal.persistent.entity.ApplicationTrx;

import java.util.List;

@Repository
public interface AplicationTrxRepository  extends JpaRepository<ApplicationTrx, String> {
    List<ApplicationTrx> findByApplicationId(String applicationId);
}
