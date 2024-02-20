package ru.hse.edu.tukach.dto.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ApplicationFromEmailCreationDto extends BaseApplicationCreationDto {

    @Email(message = "Неверный формат email'а")
    @NotEmpty(message = "Не заполнено обязательное поле - email")
    @Schema(description = "Тип заявки")
    private String initiatorEmail;
}
