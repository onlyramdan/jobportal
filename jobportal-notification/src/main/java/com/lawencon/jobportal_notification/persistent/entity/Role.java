package com.lawencon.jobportal_notification.persistent.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_roles", 
uniqueConstraints = {@UniqueConstraint(name="tb_roles_ck",columnNames = {"code","deleted_at"})})
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql="UPDATE tb_roles  SET deleted_at = NOW() WHERE id = ? AND version =?")
@Where(clause="deleted_at IS NULL")
public class Role extends MasterEntity{
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", length = 40)
    private String name;
}
