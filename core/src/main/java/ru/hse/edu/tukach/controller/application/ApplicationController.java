package ru.hse.edu.tukach.controller.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.edu.tukach.dto.application.ApplicationFromEmailCreationDto;
import ru.hse.edu.tukach.dto.application.ApplicationSource;
import ru.hse.edu.tukach.dto.rest.Response;
import ru.hse.edu.tukach.model.application.Application;
import ru.hse.edu.tukach.permissions.annotations.Permissions;
import ru.hse.edu.tukach.service.application.ApplicationService;
import ru.hse.edu.tukach.service.security.permission.CustomPermission;

@RestController
@RequestMapping(value = "/api/v1/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    @Operation(summary = "Создание заявки пользователя, отправляющего её с фронта. Возвращает id созданной заявки")
    public Response<Long> createApplication(@RequestBody ApplicationFromEmailCreationDto dto) {
        return Response.success(applicationService.save(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации по заявке пользователя, отправлявшего её с фронта")
    public Response<Application> getApplicationByIdAndInitiatorEmail(
        @Parameter(description = "Идентификатор заявки") @PathVariable Long id,
        @Parameter(description = "Email инициатора заявки") @RequestParam(name = "email") String email
    ) {
        return Response.success(applicationService.getApplicationByIdAndInitiator(id, email, ApplicationSource.EMAIL));
    }

    @PutMapping("/review/{id}")
    @Operation(summary = "Получение информации по заявке пользователя, отправлявшего её с фронта")
    @Permissions(permissions = CustomPermission.APPLICATIONS_UPDATE)
    public void startApplicationReview(@Parameter(description = "Идентификатор заявки") @PathVariable Long id) {

    }
}
