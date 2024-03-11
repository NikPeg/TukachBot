package ru.hse.edu.tukach.controller.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.edu.tukach.dto.rest.Response;
import ru.hse.edu.tukach.service.security.SecurityService;
import ru.hse.edu.tukach.service.security.permission.CustomPermission;
import ru.hse.edu.tukach.service.security.permission.UserPermissionService;

import java.util.List;
import java.util.Set;

/**
 * Контроллер для передачи на фронт прав пользователя
 */
@RestController
@RequestMapping(value = "/api/v1/security")
@RequiredArgsConstructor
public class SecurityController {

    private final UserPermissionService permissionService;
    private final SecurityService securityService;

    @GetMapping
    public Response<Void> checkLoggedIn() {
        return Response.success(null);
    }

    @GetMapping("/user-permissions")
    public Response<Set<String>> getCurrentUserPermissions() {
        return Response.success(permissionService.getCurrentUserPermissions());
    }

    @PostMapping("/generate-jwt")
    @Operation(
        summary = "Генерация jwt-токена для авторизации в методах REST API",
        description = "Метод создан для упрощения тестирования функционала"
    )
    public String generateJwt() {
        return securityService.generateJwt();
    }
}
