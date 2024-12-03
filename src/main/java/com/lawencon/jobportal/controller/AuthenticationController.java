package com.lawencon.jobportal.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.authentication.service.AuthenticationService;
import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.CreateUserRequest;
import com.lawencon.jobportal.model.request.LoginRequest;
import com.lawencon.jobportal.model.request.VerifyUserRequest;
import com.lawencon.jobportal.model.response.JwtAuthenticationResponse;
import com.lawencon.jobportal.model.response.UserSessionResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.VerifyUserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping({ "/api/v1" })
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final VerifyUserService verifyUserService;

    @PostMapping(value = "/registers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<?>> create(@Valid @RequestBody CreateUserRequest request) {
        verifyUserService.register(request);
        return ResponseEntity.ok(ResponseHelper.ok("Verification Code Send To " + request.getEmail()));
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<JwtAuthenticationResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ResponseHelper.ok(authenticationService.login(request)));
    }

    @PostMapping(value = "/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<?>> verify(@RequestBody VerifyUserRequest request) {
        verifyUserService.verify(request);
        return ResponseEntity.ok(ResponseHelper.ok("Account verified successfully"));
    }

    @PostMapping(value = "/resend", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<?>> resend(@RequestParam String email) {
        verifyUserService.resend(email);
        return ResponseEntity.ok(ResponseHelper.ok("Verification code sent"));
    }

    @GetMapping(value = "user/session")
    public ResponseEntity<WebResponse<UserSessionResponse>> getSession() {
        return ResponseEntity.ok(ResponseHelper.ok(authenticationService.getSession()));
    }
}
