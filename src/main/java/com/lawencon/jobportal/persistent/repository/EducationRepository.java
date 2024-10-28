package com.lawencon.jobportal.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistent.entity.Education;

import java.util.List;



@Repository
public interface EducationRepository extends JpaRepository<Education, String> {
    List<Education> findByProfileId(String id);

}
