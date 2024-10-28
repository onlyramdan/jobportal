package com.lawencon.jobportal.persistent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "tb_applications")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE tb_applications SET deleted_at = NOW() WHERE id = ? AND version = ?")
@Where(clause = "deleted_at IS NULL")
public class Application extends DeletableEntity {
    @ManyToOne
    @JoinColumn(name = "vacancy_trx_id", nullable = false)
    private StatusVacancyTrx statusVacancyTrx;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Profile candidate;
}
