package ru.hse.edu.tukach.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Пользователь (сотрудник компании)
 */
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    /**
     * Логин пользователя
     */
    @Id
    private String login;

    /**
     * Email сотрудника
     */
    private String email;

    /**
     * Пароль сотрудника
     */
    private String password;

    /**
     * Идентификатор роли
     */
    private Long roleId;
}
