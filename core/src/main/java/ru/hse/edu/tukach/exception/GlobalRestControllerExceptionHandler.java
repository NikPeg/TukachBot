package ru.hse.edu.tukach.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.hse.edu.tukach.dto.rest.Response;
import ru.hse.edu.tukach.exception.constant.ExceptionCode;

@Slf4j
@RestControllerAdvice
public class GlobalRestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<?> handleRuntimeException(RuntimeException exception) {
        logger.error("Unexpected server error", exception);
        return Response.fail(ExceptionCode.TUKACH_INTERNAL_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<?> handleNotFoundRestException(NotFoundException exception) {
        return Response.fail(exception.getExceptionCode(), "param", exception.getMessage());
    }
}
