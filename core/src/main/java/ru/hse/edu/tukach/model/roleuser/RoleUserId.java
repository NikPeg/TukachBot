package ru.hse.edu.tukach.model.roleuser;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Роль - пользователь
 */
@Getter
@Setter
@Embeddable
public class RoleUserId implements Serializable {

    /**
     * Идентификатор роли
     */
    private Long roleId;

    /**
     * Идентификатор пользователя
     */
    private Long userId;
}
