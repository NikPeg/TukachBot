package ru.hse.edu.tukach.dto.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractResponse<T> {

    @Schema(description = "Данные ответа")
    protected T data;

    @Schema(description = "Список ошибок")
    protected List<Error> errors = new ArrayList<>();
}
