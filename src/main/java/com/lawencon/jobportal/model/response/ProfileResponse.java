package com.lawencon.jobportal.model.response;
import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileResponse {

    private String id;
    private String userId;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String createdBy;
    private ZonedDateTime createdAt;
    private String updatedBy;
    private ZonedDateTime updatedAt;
    private Boolean isActive;
    private Integer version;
    
}
