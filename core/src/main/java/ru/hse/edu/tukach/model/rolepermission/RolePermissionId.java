package ru.hse.edu.tukach.model.rolepermission;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Роль
 */
@Getter
@Setter
@Embeddable
public class RolePermissionId implements Serializable {

    /**
     * Идентификатор роли
     */
    private Long roleId;

    /**
     * Идентификатор разрешения
     */
    private Long permissionId;
}
