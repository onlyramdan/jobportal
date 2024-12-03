package com.lawencon.jobportal.persistent.repository;

import com.lawencon.jobportal.persistent.entity.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobDescriptionRepository extends JpaRepository<JobDescription, String> {
    List<JobDescription> findByJobTitleId(String jobTitleId);
    boolean existsByJobTitleId(String jobTitleId);
}
