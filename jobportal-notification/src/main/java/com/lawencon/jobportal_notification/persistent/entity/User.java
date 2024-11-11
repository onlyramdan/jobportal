package com.lawencon.jobportal_notification.persistent.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_users",
uniqueConstraints = {
    @UniqueConstraint(name="tb_user_ck",columnNames = {"username","deleted_at"}),
    @UniqueConstraint(name="tb_user_bk",columnNames = {"email"})})
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql="UPDATE tb_users  SET deleted_at = NOW() WHERE id = ? AND version =?")
@Where(clause ="deleted_at IS NULL")
public class User extends MasterEntity {
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", length = 100)
    private String email;
    
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
