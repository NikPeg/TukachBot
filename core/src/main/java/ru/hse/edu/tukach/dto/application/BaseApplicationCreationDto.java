package ru.hse.edu.tukach.dto.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import ru.hse.edu.tukach.model.application.ApplicationType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class BaseApplicationCreationDto {

    @NotNull(message = "Не заполнено обязательное поле - тип заявки")
    @Schema(description = "Тип заявки")
    private ApplicationType type;

    @NotEmpty(message = "Не заполнено обязательное поле - тема заявки")
    @Schema(description = "Тема заявки")
    private String topic;

    @NotEmpty(message = "Не заполнено обязательное поле - текст заявки")
    @Schema(description = "Текст заявки")
    private String description;

    @Schema(description = "ФИО", nullable = true)
    private String initiatorFio;
}
