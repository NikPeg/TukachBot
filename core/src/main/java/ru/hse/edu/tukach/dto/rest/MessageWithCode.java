package ru.hse.edu.tukach.dto.rest;

import java.util.Map;

public interface MessageWithCode {

    String getCode();

    String getMessage();

    /**
     * Пример params = [("param1", "haha"), ("param2", "hihi")]
     * Тогда "some text with {param1} and {param2}" будет выглядеть "some text with haha and hihi"
     */
    default String renderWithParams(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return getMessage();
        }

        String result = getMessage();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String keyParam = "{" + entry.getKey() + "}";
            if (getMessage().contains(keyParam)) {
                result = result.replace(keyParam, entry.getValue() != null ? entry.getValue() : "null");
            }
        }
        return result;
    }
}
