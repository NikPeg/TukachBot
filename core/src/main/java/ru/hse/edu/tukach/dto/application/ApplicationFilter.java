package ru.hse.edu.tukach.dto.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import ru.hse.edu.tukach.model.application.ApplicationStatus;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class ApplicationFilter {

    @Schema(description = "Логины сотрудников, рассматривающих заявки", nullable = true)
    Set<String> reviewerLogins;

    @Schema(description = "Статусы заявок", nullable = true)
    Set<ApplicationStatus> statuses;

    @Schema(description = "Дата создания заявки 'от'", nullable = true)
    Instant dateFrom;

    @Schema(description = "Дата создания заявки 'до'", nullable = true)
    Instant dateTo;
}
