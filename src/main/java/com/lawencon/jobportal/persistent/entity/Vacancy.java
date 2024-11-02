package com.lawencon.jobportal.persistent.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "tb_vacancies",
       uniqueConstraints = {@UniqueConstraint(name = "tb_vacancies_ck", columnNames = {"code", "deleted_at"})})
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE tb_vacancies SET deleted_at = NOW() WHERE id = ? AND version = ?")
@Where(clause = "deleted_at IS NULL")
public class Vacancy extends MasterEntity {

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "pic_hr_id")
    private String picHrId;

    @ManyToOne
    @JoinColumn(name = "job_title_id", nullable = false)
    private JobTitle jobTitle;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location; 

    @ManyToOne
    @JoinColumn(name = "employee_type_id", nullable = false)
    private EmployeeType employeeType;

    @ManyToOne
    @JoinColumn(name = "experience_level_id", nullable = false)
    private ExperienceLevel experienceLevel;

    @Column(name = "min_salary", nullable = false)
    private BigDecimal minSalary;

    @Column(name = "max_salary", nullable = false)
    private BigDecimal maxSalary;

    @Column(name = "application_deadline", nullable = false)
    private LocalDate applicationDeadline;

    @Column(name = "overview", nullable = false)
    private String overview;
}
