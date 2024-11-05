package com.lawencon.jobportal.service.impl;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.lawencon.jobportal.model.request.CreateUserRequest;
import com.lawencon.jobportal.model.request.VerifyUserRequest;
import com.lawencon.jobportal.persistent.entity.VerifyUser;
import com.lawencon.jobportal.persistent.repository.VerifyUserRepository;
import com.lawencon.jobportal.service.EmailService;
import com.lawencon.jobportal.service.UserService;
import com.lawencon.jobportal.service.VerifyUserService;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VerifyUserServiceImpl implements VerifyUserService {
    private final VerifyUserRepository verifyUserRepository;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final TemplateEngine templateEngine;

    @Override
    public void register(CreateUserRequest request) {
        VerifyUser verifyUser = new VerifyUser();
        BeanUtils.copyProperties(request, verifyUser);
        verifyUser.setVerificationCode(generateVerificationCode());
        verifyUser.setVerificationExpiredAt(ZonedDateTime.now().plusMinutes(15));
        verifyUser.setIsEnable(false);
        verifyUser.setPassword(passwordEncoder.encode(request.getPassword()));

        VerifyUser verifyUserSaved = verifyUserRepository.save(verifyUser);
        try {
            String emailContent = createEmailTemplate(verifyUserSaved.getVerificationCode());
            emailService.sendVerificationEmail(verifyUserSaved.getEmail(), "Email Verification", emailContent);
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send verification email");
        }
    }

    @Override
    public void verify(VerifyUserRequest input) {
        Optional<VerifyUser> optionalUser = verifyUserRepository.findByEmail(input.getEmail());
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

        VerifyUser user = optionalUser.get();
        if (user.getVerificationExpiredAt().isBefore(ZonedDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Verification code has expired");
        }

        if (user.getVerificationCode().equals(input.getVerificationCode())) {
            user.setVerificationCode(null);
            user.setVerificationExpiredAt(null);
            user.setIsEnable(true);
            verifyUserRepository.save(user);

            CreateUserRequest request = new CreateUserRequest();
            request.setEmail(user.getEmail());
            request.setPassword(user.getPassword());
            request.setRoleId(user.getRoleId());
            request.setUsername(user.getUsername());
            userService.save(request);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid verification code");
        }
    }

    @Override
    public void resend(String email) {
        Optional<VerifyUser> optionalUser = verifyUserRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            VerifyUser user = optionalUser.get();
            if (user.getIsEnable()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationExpiredAt(ZonedDateTime.now().plusHours(1));
            verifyUserRepository.save(user);
            try {
                String emailContent = createEmailTemplate(user.getVerificationCode());
                emailService.sendVerificationEmail(user.getEmail(), "Resend Verification Code", emailContent);
            } catch (MessagingException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to resend verification email");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); 
        return String.valueOf(otp);
    }
    private String createEmailTemplate(String verificationCode) {
        try {
            Context context = new Context();
            context.setVariable("verificationCode", verificationCode);
            return templateEngine.process("templates/verification_email_template.html", context);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to load email template");
        }
    }
}
