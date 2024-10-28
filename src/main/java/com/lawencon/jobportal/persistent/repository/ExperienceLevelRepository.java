package com.lawencon.jobportal.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistent.entity.ExperienceLevel;

@Repository
public interface ExperienceLevelRepository extends JpaRepository<ExperienceLevel, String> {
}