package com.parabank.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;


public class JacksonUtils {
    @Getter
    private static final ObjectMapper mapper = new ObjectMapper();

    private JacksonUtils() {
    }

}
