package com.lawencon.jobportal.persistent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_aplication_trx")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE tb_aplication_trx SET deleted_at = NOW() WHERE id = ? AND version = ?")
@Where(clause = "deleted_at IS NULL")
public class ApplicationTrx extends DeletableEntity {

    @ManyToOne
    @JoinColumn(name = "aplication_id", nullable = false)
    private Application application;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusAplication statusApplication;

    @ManyToOne
    @JoinColumn(name = "stage_id", nullable = false)
    private Stage stage;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "score", nullable = false, precision = 10, scale = 2)
    private BigDecimal score;
}