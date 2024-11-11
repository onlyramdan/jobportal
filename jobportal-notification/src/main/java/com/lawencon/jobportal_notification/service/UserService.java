package com.lawencon.jobportal_notification.service;

import java.util.List;
import com.lawencon.jobportal_notification.persistent.entity.User;


public interface UserService {
    List<User> findAllCandidate();
}
