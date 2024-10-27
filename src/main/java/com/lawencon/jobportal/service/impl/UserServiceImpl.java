package com.lawencon.jobportal.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.CreateUserRequest;
import com.lawencon.jobportal.model.request.UpdateUserRequest;
import com.lawencon.jobportal.persistent.entity.Role;
import com.lawencon.jobportal.persistent.entity.User;
import com.lawencon.jobportal.persistent.repository.UserRepository;
import com.lawencon.jobportal.service.RoleService;
import com.lawencon.jobportal.service.UserService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;
import java.time.ZoneOffset;


@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleService  roleService;
    
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found")
        );
    }

    @Override
    public User update(UpdateUserRequest request) {
        Role role = roleService.findEntityById(request.getRoleId());
        User user = findById(request.getId());
        BeanUtils.copyProperties(request, user);
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public User save(CreateUserRequest request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);
        Role role = roleService.findEntityById(request.getRoleId());
        user.setRole(role);
        user.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        user.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        user.setCreatedBy("system");
        user.setUpdatedBy("system");
        user.setIsActive(true);
        return userRepository.save(user);
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findEntityById(String id) {
        return userRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found")
        );
    }
}
