package ru.hse.edu.tukach.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.hse.edu.tukach.model.permission.Permission;
import ru.hse.edu.tukach.model.user.User;
import ru.hse.edu.tukach.permissions.PermissionsChecker;
import ru.hse.edu.tukach.permissions.aspect.AbstractPermissionsAspect;
import ru.hse.edu.tukach.permissions.aspect.DefaultPermissionAspect;
import ru.hse.edu.tukach.permissions.providers.CurrentUserPermissionsProvider;
import ru.hse.edu.tukach.permissions.service.PermissionService;
import ru.hse.edu.tukach.service.security.SecurityService;
import ru.hse.edu.tukach.service.security.permission.UserPermissionService;

import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
public class PermissionsConfig {

    // Предоставляет список разрешенных пермишенов текущего юзера.
    // В данной реализации они хранятся как Authorities в секьюрити контексте
    @Bean
    public CurrentUserPermissionsProvider currentUserPermissionsProvider(UserPermissionService permissionService) {
        return () -> {
            System.out.println("lalalala loloololololoolol 1");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication.getAuthorities() == null) {
                return Collections.emptySet();
            }

            User user = (User) authentication.getPrincipal();
            System.out.println("lalalala loloololololoolol 2");
            // todo добавлять роли при аутентификации в authorities.
            return permissionService.getAllowedPermissionsForUser(user)
                .stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());
        };
    }

    @Bean
    public UserPermissionService permissionService(SecurityService securityService) {
        return new UserPermissionService(securityService);
    }

    @Bean
    public PermissionsChecker permissionChecker(SecurityService securityService) {
        UserPermissionService permissionService = permissionService(securityService);
        return new PermissionsChecker(currentUserPermissionsProvider(permissionService));
    }

    @Bean
    public AbstractPermissionsAspect permissionsAspect(SecurityService securityService) {
        return new DefaultPermissionAspect(permissionChecker(securityService));
    }

}
