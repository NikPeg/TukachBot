package ru.hse.edu.tukach.dto.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ApplicationFromTelegramCreationDto extends BaseApplicationCreationDto {

    @NotEmpty(message = "Не заполнено обязательное поле - Telegram-ник")
    @Schema(description = "Тип заявки")
    private String initiatorTg;

    @NotEmpty(message = "Не заполнено обязательное поле - заполняемое поле")
    @Schema(description = "Заполняемое поле")
    private String currentField;

    @Override
    public BaseApplicationCreationDto setDescription(String description) {
        if (!StringUtils.hasText(description)) {
            throw new IllegalArgumentException("Текст заявки не заполнен");
        }
        return super.setDescription(description);
    }

    @Override
    public BaseApplicationCreationDto setTopic(String topic) {
        if (!StringUtils.hasText(topic)) {
            throw new IllegalArgumentException("Тема заявки не заполнена");
        }
        return super.setTopic(topic);
    }
}
