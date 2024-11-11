package com.lawencon.jobportal_notification.persistent.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lawencon.jobportal_notification.persistent.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByRole_Code(String code);
}

