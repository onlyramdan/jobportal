package com.lawencon.jobportal.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistent.entity.StatusAplication;

@Repository
public interface StatusAplicationRepository extends JpaRepository<StatusAplication, String> {
    
}
