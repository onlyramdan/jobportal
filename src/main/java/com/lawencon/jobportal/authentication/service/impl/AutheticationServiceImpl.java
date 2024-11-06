package com.lawencon.jobportal.authentication.service.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.authentication.service.AuthenticationService;
import com.lawencon.jobportal.authentication.service.JwtService;
import com.lawencon.jobportal.model.request.LoginRequest;
import com.lawencon.jobportal.model.response.JwtAuthenticationResponse;
import com.lawencon.jobportal.persistent.entity.User;
import com.lawencon.jobportal.service.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AutheticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        Optional<User> userOpt = userService.getUsername(loginRequest.getUsername(), loginRequest.getPassword());
        if (userOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid username or password");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserDetails UserPrinciple = userService.userDetailsService().loadUserByUsername(loginRequest.getUsername());
        User user = userOpt.get();
        String token = jwtService.generateToken(UserPrinciple);
        return JwtAuthenticationResponse.builder().token(token).build();
    }

}
