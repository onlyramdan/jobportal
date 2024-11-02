package com.lawencon.jobportal.persistent.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lawencon.jobportal.persistent.entity.VerifyUser;

public interface VerifyUserRepository extends JpaRepository<VerifyUser, String> {
    Optional<VerifyUser> findByEmail(String email);
    Optional<VerifyUser> findByVerificationCode(String verificationCode);
}
