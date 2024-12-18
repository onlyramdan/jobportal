package com.lawencon.jobportal.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id; 
    private String username; 
    private String email;
    private String roleId;
    private String role;
    private Boolean isActive;
    private Long version;
}
