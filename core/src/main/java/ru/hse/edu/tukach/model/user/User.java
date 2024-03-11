package ru.hse.edu.tukach.model.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.hse.edu.tukach.model.role.Role;

import java.time.Instant;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Пользователь (сотрудник компании)
 */
@Getter
@Setter
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
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
     * ФИО сотрудника
     */
    private String fullName;

    /**
     * Дополнительная информация о сотруднике
     */
    private String additionalInfo;


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

    /*@ManyToMany
    @JoinTable(
        name = "role_user",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles;*/
}
