package com.lawencon.jobportal.persistent.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_job_descriptions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql="UPDATE tb_job_descriptions  SET deleted_at = NOW() WHERE id = ? AND version =?")
@Where(clause ="deleted_at IS NULL")
public class JobDescription extends MasterEntity {
    @ManyToOne
    @JoinColumn(name = "job_title_id", nullable = false)
    private JobTitle jobTitle;

    @Column(name = "title_desc", length = 100, nullable = false)
    private String titleDesc;

    @Column(name = "description", nullable = false)
    private String description;
}