package ru.hse.edu.tukach.service.security.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hse.edu.tukach.exception.NotFoundException;
import ru.hse.edu.tukach.exception.constant.ExceptionCode;
import ru.hse.edu.tukach.model.permission.Permission;
import ru.hse.edu.tukach.model.user.User;
import ru.hse.edu.tukach.permissions.service.PermissionService;
import ru.hse.edu.tukach.service.security.SecurityService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPermissionService extends PermissionService {

    private final SecurityService securityService;

    public Set<String> getCurrentUserPermissions() {
        User currentUser = getCurrentUser();
        return getAllowedPermissionsForUser(currentUser)
            .stream()
            .map(Permission::getName)
            .collect(Collectors.toSet());
    }

    public User getCurrentUser() {
        return securityService.getAuthUser().orElseThrow(
            () -> new NotFoundException(ExceptionCode.USER_NOT_FOUND, "Current user not found")
        );
    }

    /*public void checkAccess(String customPermission) {
        User currentUser = getCurrentUser();
        if (!userHasPermission(currentUser, customPermission)) {
            throw new NoPermissionException(currentUser.getLogin() + " doesn't have permission " + customPermission);
        }
    }

    public boolean hasPermission(String customPermission) {
        User currentUser = getCurrentUser();
        return userHasPermission(currentUser, customPermission);
    }*/
}
