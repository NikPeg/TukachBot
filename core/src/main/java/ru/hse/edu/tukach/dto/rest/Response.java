package ru.hse.edu.tukach.dto.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Результат выполнения запроса")
public class Response<T> extends AbstractResponse<T> {

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }

    public static <T> Response<T> fail(MessageWithCode message) {
        return fail(message, Collections.emptyMap());
    }

    public static <T> Response<T> fail(MessageWithCode message, String paramKey, String paramValue) {
        Map<String, String> params = new HashMap<>();
        params.put(paramKey, paramValue);
        return fail(message, params);
    }

    public static <T> Response<T> fail(MessageWithCode message, Map<String, String> params) {
        return fail(message.getCode(), message.renderWithParams(params));
    }

    public static <T> Response<T> fail(String code, String message) {
        Error error = new Error(code, message);
        return createResponse(error);
    }

    private static <T> Response<T> createResponse(Error error) {
        return createResponse(Collections.singletonList(error));
    }

    private static <T> Response<T> createResponse(List<Error> errorList) {
        Response<T> response = new Response<>();
        response.setErrors(errorList);
        return response;
    }
}
