package com.parabank.utils.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import lombok.Getter;


public class JacksonUtil {
    @Getter
    private static final ObjectMapper mapper = new ObjectMapper();

    private JacksonUtil() {
    }

    public static <T> T deserialize(APIResponse response, Class<T> targetClass) throws JsonProcessingException {
        return mapper.readValue(response.text(), targetClass);
    }
}
