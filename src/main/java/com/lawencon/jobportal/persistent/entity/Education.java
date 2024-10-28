package com.lawencon.jobportal.persistent.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_educations")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql="UPDATE tb_educations  SET deleted_at = NOW() WHERE id = ? AND version =?")
@Where(clause ="deleted_at IS NULL")
public class Education extends MasterEntity {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "gpa", nullable = false)
    private Double gpa;

    @Column(name = "start_year", nullable = false)
    private Integer startYear;

    @Column(name = "end_year", nullable = false)
    private Integer endYear;

    @Column(name = "major", nullable = false, length = 100)
    private String major;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_level_id", nullable = false)
    private EducationLevel educationLevel;
}
