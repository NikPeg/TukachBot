package ru.hse.edu.tukach.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.hse.edu.tukach.dto.rest.MessageWithCode;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode  implements MessageWithCode {

    TUKACH_INTERNAL_ERROR("Unexpected server error"),
    APPLICATION_NOT_FOUND("{param}");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
