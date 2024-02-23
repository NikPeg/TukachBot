package ru.hse.edu.tukach.model.application;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApplicationType {

    VIOLATION("Нарушение"),
    COMPLIMENT("Похвалить"),
    OTHER("Другое");

    private final String message;
}
