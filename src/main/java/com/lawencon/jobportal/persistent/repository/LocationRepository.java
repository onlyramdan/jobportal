package com.lawencon.jobportal.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistent.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, String>, JpaSpecificationExecutor<Location> {
    Boolean existsByCode(String code);
}