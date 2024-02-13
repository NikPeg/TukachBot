package ru.hse.edu.tukach.model.rolepermission;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

/**
 * Роль
 */
@Getter
@Setter
@Embeddable
public class RolePermissionId {

    /**
     * Идентификатор роли
     */
    private Long roleId;

    /**
     * Идентификатор разрешения
     */
    private Long permissionId;
}
