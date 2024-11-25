package com.cheolhyeon.communityapi.module.auth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonPrettyPrinter {

    private JsonPrettyPrinter() {
        throw new AssertionError("Utility class");
    }
    public static String getPrettyForPrint(ObjectMapper mapper, Object value) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }
}
