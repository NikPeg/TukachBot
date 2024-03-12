package ru.hse.edu.tukach.model.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationType {

    VIOLATION("Нарушение"),
    COMPLIMENT("Благодарность"),
    OTHER("Другое");

    private final String message;
}
