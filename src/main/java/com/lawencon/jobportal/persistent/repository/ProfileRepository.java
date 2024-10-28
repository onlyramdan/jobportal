package com.lawencon.jobportal.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistent.entity.Profile;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    Optional<Profile> findByUserId(String id);
}
