package com.lawencon.jobportal_notification.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.jobportal_notification.persistent.entity.User;
import com.lawencon.jobportal_notification.persistent.repository.UserRepository;
import com.lawencon.jobportal_notification.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> findAllCandidate() {
        return userRepository.findByRole_Code("CD");
    }
    
    
}
