package com.lawencon.jobportal_notification.persistent.entity;

import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class DeletableEntity extends AuditableEntity {
    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;
}
