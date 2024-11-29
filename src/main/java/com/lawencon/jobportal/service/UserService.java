package com.lawencon.jobportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.lawencon.jobportal.model.request.CreateUserRequest;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.UpdateUserRequest;
import com.lawencon.jobportal.model.response.UserResponse;
import com.lawencon.jobportal.persistent.entity.User;

public interface UserService {
    List<UserResponse> findAll();
    UserResponse findById(String id);
    User save(CreateUserRequest request);
    void update(UpdateUserRequest request);
    void delete(String id);
    User findEntityById(String id);
    UserDetailsService userDetailsService();
    Optional<User> getUsername(String username, String password);
    Boolean checkUsername(String username);
    Boolean checkEmail(String email);
    Page<UserResponse> findAll(PagingRequest pagingRequest, String inquiry);
    void createByAdmin(CreateUserRequest request);
}
