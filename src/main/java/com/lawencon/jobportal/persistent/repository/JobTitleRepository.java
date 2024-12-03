package com.lawencon.jobportal.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistent.entity.JobTitle;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitle, String>, JpaSpecificationExecutor<JobTitle> {
    boolean existsById(String id);
}
