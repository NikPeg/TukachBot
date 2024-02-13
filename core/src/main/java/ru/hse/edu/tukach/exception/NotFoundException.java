package ru.hse.edu.tukach.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.hse.edu.tukach.exception.constant.ExceptionCode;

@Getter
@AllArgsConstructor
public class NotFoundException extends RuntimeException {

    private final ExceptionCode exceptionCode;
    private final String message;

}
