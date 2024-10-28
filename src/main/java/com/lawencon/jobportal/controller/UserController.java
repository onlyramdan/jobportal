package com.lawencon.jobportal.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.CreateUserRequest;
import com.lawencon.jobportal.model.request.UpdateUserRequest;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@AllArgsConstructor
@RequestMapping({"/api/v1/users"})
public class UserController {
    private final UserService userService;

    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<?>>  createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(ResponseHelper.ok(userService.save(request)));
    }

    @GetMapping()
    public  ResponseEntity<WebResponse<?>> findAll(){
        return ResponseEntity.ok(ResponseHelper.ok(userService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<?>>  findById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(userService.findById(id)));
    }

    @PutMapping()
    public ResponseEntity<WebResponse<?>>  update(@RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(ResponseHelper.ok(userService.update(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<?>>  delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok());
    }

}
