package com.lawencon.jobportal.service;

import java.util.List;

import com.lawencon.jobportal.model.request.CreateUserRequest;
import com.lawencon.jobportal.model.request.UpdateUserRequest;
import com.lawencon.jobportal.persistent.entity.User;

public interface UserService {
    List<User> findAll();
    User findById(String id);
    User save(CreateUserRequest request);
    User update(UpdateUserRequest request);
    void delete(String id);
    User findEntityById(String id);
}
