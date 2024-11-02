package com.lawencon.jobportal.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.authentication.service.AuthenticationService;
import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.CreateUserRequest;
import com.lawencon.jobportal.model.request.LoginRequest;
import com.lawencon.jobportal.model.response.JwtAuthenticationResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.persistent.entity.User;
import com.lawencon.jobportal.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping({"/api/v1"})
public class AuthenticationController {
    private final  UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/registers"  ,consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<?>> create(@Valid @RequestBody CreateUserRequest request) {
        User userResponse = userService.save(request);
        return ResponseEntity.ok(ResponseHelper.ok(userResponse));
    }
    
    @PostMapping(value = "/login" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<JwtAuthenticationResponse>> login(@RequestBody LoginRequest  request) {
        return  ResponseEntity.ok(ResponseHelper.ok(authenticationService.login(request)));
    }

}
