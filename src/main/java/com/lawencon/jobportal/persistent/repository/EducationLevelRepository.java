package com.lawencon.jobportal.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lawencon.jobportal.persistent.entity.EducationLevel;

public interface EducationLevelRepository extends JpaRepository<EducationLevel, String> {   
}
