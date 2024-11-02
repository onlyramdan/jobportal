package com.lawencon.jobportal.authentication.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    boolean validateToken(String token, UserDetails userDetails);
    String extractUserName(String token);
}
