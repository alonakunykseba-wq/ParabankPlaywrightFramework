package com.parabank.utils.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;


public class JacksonUtil {
    @Getter
    private static final ObjectMapper mapper = new ObjectMapper();

    private JacksonUtil() {
    }

}
