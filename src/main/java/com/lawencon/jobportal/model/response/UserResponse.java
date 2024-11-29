package com.lawencon.jobportal.model.response;


import com.lawencon.jobportal.persistent.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String userId; 
    private String username; 
    private String email;
    private String role;
    private Boolean isActive;
    private Long version;
}
