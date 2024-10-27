package com.lawencon.jobportal.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MasterResponse {
    private String id;
    private String code;
    private String name;
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
    private Boolean isActive;
    private Long version;
}
