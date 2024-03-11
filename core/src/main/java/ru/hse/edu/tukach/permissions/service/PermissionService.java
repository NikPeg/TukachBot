package ru.hse.edu.tukach.permissions.service;

import org.springframework.stereotype.Service;
import ru.hse.edu.tukach.model.permission.Permission;
import ru.hse.edu.tukach.model.role.Role;
import ru.hse.edu.tukach.model.rolepermission.RolePermission;
import ru.hse.edu.tukach.model.user.User;

import java.util.Collections;
import java.util.Set;

public class PermissionService {

    /**
     * Получение списка разрешений для юзера согласно его ролям.
     */
    public Set<Permission> getAllowedPermissionsForUser(User user) {
        if (user == null) {
            return Collections.emptySet();
        }
        return null;
    }

    /*public boolean userHasPermission(User user, String permission) {
        return getAllowedPermissionsForUser(user)
            .stream()
            .anyMatch(p -> p.getName().equals(permission));
    }

    public Set<RolePermission> getAllowedPermissionsForRole(Role role) {
        if (role == null) {
            return Collections.emptySet();
        }
        return null;
    }*/
}
