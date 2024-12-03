package com.lawencon.jobportal.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistent.entity.Application;
import java.util.List;

@Repository
public interface AplicationRepository extends JpaRepository<Application, String>  {  
       List<Application> findByStatusVacancyTrxId(String statusVacancyTrxId); 
       List<Application> findByCandidateId(String id);
}
