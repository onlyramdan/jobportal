package com.lawencon.jobportal.persistent.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lawencon.jobportal.persistent.entity.Vacancy;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, String> {
    List<Vacancy> findByJobTitleId(String jobTitleId);
    List<Vacancy> findByEmployeeTypeId(String employeeTypeId);
    List<Vacancy> findByExperienceLevelId(String experienceLevelId); 
}
