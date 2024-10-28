package com.lawencon.jobportal.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistent.entity.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, String> {
    Optional<User> findByUsername(String username);
}
