package com.lawencon.jobportal.persistent.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistent.entity.JobSpecification;

@Repository
public interface JobSpecificationRepository extends JpaRepository<JobSpecification, String> {
    List<JobSpecification> findByJobTitleId(String jobTitleId);
    void deleteByJobTitleId(String id);
    boolean existsByJobTitleId(String jobTitleId);
}
