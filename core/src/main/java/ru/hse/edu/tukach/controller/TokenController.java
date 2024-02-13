package ru.hse.edu.tukach.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.edu.tukach.service.token.TokenService;

@RestController
@RequestMapping(value = "/api/v1/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/generate-jwt")
    @Operation(
        summary = "Генерация jwt-токена для авторизации в методах REST API",
        description = "Метод создан для упрощения тестирования функционала")
    public String generateJwt() {
        return tokenService.generateJwt();
    }
}
