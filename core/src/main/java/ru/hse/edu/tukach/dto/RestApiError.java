package ru.hse.edu.tukach.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain = true)
public class RestApiError {

    @Schema(description = "Код ошибки")
    private String code;

    @Schema(description = "Комментарий к ошибке")
    private String message;
}
