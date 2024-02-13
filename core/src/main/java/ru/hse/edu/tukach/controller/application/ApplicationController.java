package ru.hse.edu.tukach.controller.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.edu.tukach.dto.rest.Response;
import ru.hse.edu.tukach.model.application.Application;
import ru.hse.edu.tukach.service.application.ApplicationService;

@RestController
@RequestMapping(value = "/api/v1/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/by-email/{id}")
    @Operation(summary = "Получение информации по заявке пользователя, отправлявшего её с фронта")
    public Response<Application> getApplicationByIdAndInitiatorEmail(
        @Parameter(description = "Идентификатор заявки") @PathVariable Long id,
        @Parameter(description = "Email инициатора заявки") @RequestParam(name = "email") String email
    ) {
        return Response.success(applicationService.getApplicationByInitiatorEmail(id, email));
    }
}
