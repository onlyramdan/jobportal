package com.lawencon.jobportal.persistent.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_notifications")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql="UPDATE tb_notifications  SET deleted_at = NOW() WHERE id = ? AND version =?")
@Where(clause="deleted_at IS NULL")
public class Notification extends MasterEntity {
    @Column(name = "title", nullable = false)
    private String  title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_vacancy_trx_id")
    private StatusVacancyTrx statusVacancyTrxId;
    
    @Column(name = "message", nullable = false)
    private String  message;

    @Column(name = "is_read", nullable=false)
    private Boolean isRead;
}
