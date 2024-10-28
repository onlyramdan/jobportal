package com.lawencon.jobportal.persistent.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_profiles", 
       uniqueConstraints = {
           @UniqueConstraint(name = "tb_profiles_bka", columnNames = "user_id"),
           @UniqueConstraint(name = "tb_profiles_bkb", columnNames = "phone_number")
       })
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql="UPDATE tb_profiles  SET deleted_at = NOW() WHERE id = ? AND version =?")
@Where(clause ="deleted_at IS NULL")
public class Profile extends MasterEntity {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "address", nullable = false)
    private String address;
}
