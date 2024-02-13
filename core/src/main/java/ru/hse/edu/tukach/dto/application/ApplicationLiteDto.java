package ru.hse.edu.tukach.dto.application;

import lombok.Getter;
import lombok.Setter;
import ru.hse.edu.tukach.model.application.ApplicationStatus;
import ru.hse.edu.tukach.model.application.ApplicationType;

import java.time.Instant;

@Getter
@Setter
public class ApplicationLiteDto {

    private Long id;

    private ApplicationStatus status;

    private ApplicationType type;

    private String topic;

    private Instant createdDateTime;
}
