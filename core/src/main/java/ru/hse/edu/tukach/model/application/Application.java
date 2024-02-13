package ru.hse.edu.tukach.model.application;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Заявка
 */
@Getter
@Setter
@Entity
@Table(name = "application")
@EntityListeners(AuditingEntityListener.class)
public class Application {

    /**
     * Идентификатор заявки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Статус заявки
     */
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    /**
     * Тип заявки
     */
    @Enumerated(EnumType.STRING)
    private ApplicationType type;

    /**
     * Тема
     */
    private String topic;

    /**
     * Суть заявки
     */
    private String description;

    /**
     * E-mail заявителя
     */
    private String initiatorEmail;

    /**
     * Telegram заявителя
     */
    private String initiatorTg;

    /**
     * ФИО заявителя
     */
    private String initiatorFio;

    /**
     * Последнее заполненное пользователем поле
     * В Telegram-боте поля заполняются последовательно, поэтому сохраняем последнее заполненное
     */
    private ApplicationField lastFilledField;

    /**
     * Логин рассмотрителя заявки (user.login)
     */
    private String reviewerLogin;

    /**
     * Информация об ответе на заявку
     */
    private String reviewerResponse;

    /**
     * Дата создания заявления
     */
    @CreatedDate
    @Column(name = "created_dttm")
    private Instant createdDateTime;

    /**
     * Дата изменения записи
     */
    @LastModifiedDate
    @Column(name = "last_modified_dttm")
    private Instant lastModifiedDateTime;
}
