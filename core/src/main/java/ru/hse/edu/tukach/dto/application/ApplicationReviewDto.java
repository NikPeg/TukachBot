package ru.hse.edu.tukach.dto.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import ru.hse.edu.tukach.model.application.ApplicationStatus;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ApplicationReviewDto {

    @Schema(description = "Статус заявки")
    @NotNull(message = "Не заполнено обязательное поле - статус, в который переходит заявка")
    private ApplicationStatus status;

    // todo брать из security-контекста
    @Schema(description = "Логин рассмотрителя заявки")
    private String reviewerLogin;

    @Schema(description = "Информация об ответе на заявку", nullable = true)
    private String reviewerResponse;
}
