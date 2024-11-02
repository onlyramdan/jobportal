package com.lawencon.jobportal.persistent.entity;

import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_verify_users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VerifyUser extends AuditableEntity {
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "role_id", nullable = false)
    private String roleId;

    @Column(name = "is_enable", nullable = true)
    private Boolean isEnable;

    @Column(name = "verification_code", nullable = true)
    private String verificationCode;

    @Column(name = "verfication_expired_at", nullable = true)
    private ZonedDateTime verificationExpiredAt;

}
