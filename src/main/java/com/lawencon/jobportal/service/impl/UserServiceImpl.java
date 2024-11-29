package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.authentication.model.UserPrinciple;
import com.lawencon.jobportal.helper.SpecificationHelper;
import com.lawencon.jobportal.model.request.CreateProfileRequest;
import com.lawencon.jobportal.model.request.CreateUserRequest;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.UpdateUserRequest;
import com.lawencon.jobportal.model.response.UserResponse;
import com.lawencon.jobportal.persistent.entity.Role;
import com.lawencon.jobportal.persistent.entity.User;
import com.lawencon.jobportal.persistent.repository.UserRepository;
import com.lawencon.jobportal.service.ProfileService;
import com.lawencon.jobportal.service.RoleService;
import com.lawencon.jobportal.service.UserService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;
import java.time.ZoneOffset;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
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
        user.setPassword(request.getPassword());
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
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    @Override
    public Optional<User> getUsername(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found")
        );
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Password");
        }
        return Optional.of(user);

    }

    @Override
    public UserDetailsService userDetailsService() {
      return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not Exist"));

                List<GrantedAuthority>  authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.getRole().getCode()));
                return UserPrinciple.builder().user(user).role(user.getRole()).authorities(authorities).build();
            }
        };
    }

    @Override
    public Boolean checkUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public Boolean checkEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public Page<UserResponse> findAll(PagingRequest pagingRequest, String inquiry) {
        PageRequest pageRequest  = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize(),
        SpecificationHelper.createSort(pagingRequest.getSortBy()));
        Specification<User>  spec  = Specification.where(null);
        if (inquiry  != null) {
            spec = spec.and(SpecificationHelper.inquiryFilter(Arrays.asList("username","email", "role.name"), inquiry));
        }
        Page<User> userResponse = userRepository.findAll(spec,pageRequest);
        List<UserResponse> responses = userResponse.getContent().stream().map(user ->{
            UserResponse response = new UserResponse();
            BeanUtils.copyProperties(user, response);
            response.setRole(user.getRole().getName());
            return response;
        }).toList();
        return new PageImpl<>(responses, pageRequest, userResponse.getTotalElements());
    }

    @Override
    public void createByAdmin(CreateUserRequest request) {
        if(checkUsername(request.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exist");
        }
        if (checkEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exist");
        }

        User user = save(request);
        CreateProfileRequest profileRequest = new CreateProfileRequest();
        profileRequest.setUser(user);
        profileService.save(profileRequest);
    }

    
}
