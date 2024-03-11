package ru.hse.edu.tukach.permissions;


import lombok.RequiredArgsConstructor;
import ru.hse.edu.tukach.permissions.annotations.Permissions;
import ru.hse.edu.tukach.permissions.exceptions.NoPermissionException;
import ru.hse.edu.tukach.permissions.providers.CurrentUserPermissionsProvider;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Класс, проверяющий наличие пермишенов у текущего пользователя
 */
@RequiredArgsConstructor
public class PermissionsChecker {

    private final CurrentUserPermissionsProvider currentUserPermissionsProvider;

    /**
     * Проверяет наличие пермишенов у текущего пользователя
     */
    public void checkPermissions(Method method) {
        Set<String> requiredPermissions = extractPermissions(method);

        Permissions permissionsAnnotation = method.getAnnotation(Permissions.class);
        PermissionMode mode = permissionsAnnotation == null ? PermissionMode.ALL : permissionsAnnotation.mode();

        checkPermissions(requiredPermissions, mode, method.getName(), method.getDeclaringClass().getSimpleName());
    }

    /**
     * Проверяет наличие пермишенов у текущего пользователя
     */
    public void checkPermissions(Set<String> requiredPermissions, PermissionMode mode, String methodName,
                                 String className) {
        Set<String> userPermissions = currentUserPermissionsProvider.currentUserPermissions();
        System.out.println("lalalalalalalal 1");

        switch (mode) {
            case ANY -> matchAnyOrThrow(requiredPermissions, userPermissions, methodName, className);
            case ALL -> matchAllOrThrow(requiredPermissions, userPermissions, methodName, className);
            default -> throw new UnsupportedOperationException("Unknown PermissionMode: " + mode);
        }
    }

    /**
     * Проверяет наличие пермишенов у текущего пользователя.
     */
    public void checkPermission(String permission, String methodName, String className) {
        Set<String> userPermissions = currentUserPermissionsProvider.currentUserPermissions();
        matchAnyOrThrow(Collections.singleton(permission), userPermissions, methodName, className);
    }

    /**
     * Проверяет наличие пермишенов у текущего пользователя
     */
    public void checkPermission(String permission) {
        Set<String> userPermissions = currentUserPermissionsProvider.currentUserPermissions();
        matchAnyOrThrow(Collections.singleton(permission), userPermissions, null, null);
    }

    /**
     * Проверяет наличие пермишена у текущего пользователя
     */
    public boolean hasPermission(String permission) {
        return hasPermission(Collections.singleton(permission), PermissionMode.ANY);
    }

    /**
     * Проверяет наличие пермишенов у текущего пользователя
     */
    public boolean hasPermission(Set<String> requiredPermissions, PermissionMode mode) {
        Set<String> userPermissions = currentUserPermissionsProvider.currentUserPermissions();
        return switch (mode) {
            case ANY -> matchAny(requiredPermissions, userPermissions);
            case ALL -> matchAll(requiredPermissions, userPermissions);
        };
    }

    private void matchAllOrThrow(Set<String> requiredPermissions, Set<String> userPermissions, String methodName,
                                 String className) {
        if (matchAll(requiredPermissions, userPermissions)) {
            return;
        }
        throw new NoPermissionException(
            "User does not have a permission(s).",
            requiredPermissions, userPermissions, PermissionMode.ALL, methodName, className
        );
    }

    private void matchAnyOrThrow(Set<String> requiredPermissions, Set<String> userPermissions, String methodName,
                                 String className) {
        if (!matchAny(requiredPermissions, userPermissions)) {
            return;
        }
        throw new NoPermissionException(
            "User does not have a proper permission.",
            requiredPermissions, userPermissions, PermissionMode.ANY, methodName, className
        );
    }

    private boolean matchAny(Set<String> requiredPermissions, Set<String> userPermissions)
        throws NoPermissionException {
        if (requiredPermissions.isEmpty()) {
            return true;
        }
        for (String userPermission : userPermissions) {
            if (requiredPermissions.contains(userPermission)) {
                return true;
            }
        }
        return false;
    }

    private boolean matchAll(Set<String> requiredPermissions, Set<String> userPermissions)
        throws NoPermissionException {
        if (requiredPermissions.isEmpty()) {
            return true;
        }
        for (String requiredPermission : requiredPermissions) {
            if (!userPermissions.contains(requiredPermission)) {
                return false;
            }
        }
        return true;
    }

    private Set<String> extractPermissions(Method method) {
        Set<String> permissions = new HashSet<>();
        if (method == null) {
            return permissions;
        }

        Permissions permissionsAnnotation = method.getAnnotation(Permissions.class);
        if (permissionsAnnotation != null) {
            permissions.addAll(extractPermissions(permissionsAnnotation));
        }

        return permissions;
    }

    private Set<String> extractPermissions(Permissions permissions) {
        if (permissions == null) {
            return new HashSet<>();
        }
        return Arrays
            .stream(permissions.permissions())
            .collect(Collectors.toSet());
    }
}
