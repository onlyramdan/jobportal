package com.lawencon.jobportal.authentication.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.lawencon.jobportal.persistent.entity.User;

public interface JwtService {
    String generateToken(UserDetails userDetails, User user);
    boolean validateToken(String token, UserDetails userDetails);
    String extractUserName(String token);
}
