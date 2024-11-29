package com.lawencon.jobportal.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.MasterRequest;
import com.lawencon.jobportal.model.response.MasterResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.persistent.entity.Role;
import com.lawencon.jobportal.service.RoleService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping({"/api/v1/roles"})
public class RoleController {
    private final  RoleService roleService;
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<MasterResponse> createRole(@RequestBody MasterRequest request) {
        return ResponseEntity.ok(roleService.save(request));
    }

    @GetMapping()
    public ResponseEntity<WebResponse<List<MasterResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(roleService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody Role request) {    
        return ResponseEntity.ok(roleService.update(request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        roleService.delete(id);
    }
            
    @GetMapping("/code/{code}")
    public ResponseEntity<WebResponse<?>> findByCode(@PathVariable("code") String code) {
        return ResponseEntity.ok(ResponseHelper.ok(roleService.findByCode(code)));
    }
}
