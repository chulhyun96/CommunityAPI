package com.cheolhyeon.communityapi.module.auth.dto.error;

import com.cheolhyeon.communityapi.module.auth.type.AuthErrorStatus;
import com.cheolhyeon.communityapi.module.auth.util.JsonPrettyPrinter;
import com.cheolhyeon.communityapi.common.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public final class HandleAuthErrorFactory {

    private HandleAuthErrorFactory() {
        throw new AssertionError("Util Class");
    }

    public static String getErrorResponse(ObjectMapper objectMapper, AuthErrorStatus errorStatus) throws JsonProcessingException {
        if (errorStatus == AuthErrorStatus.LOGIN_FIRST) {
            return JsonPrettyPrinter.getPrettyForPrint(objectMapper,
                    ErrorResponse.create(AuthErrorStatus.LOGIN_FIRST));
        }
        return JsonPrettyPrinter.getPrettyForPrint(objectMapper,
                ErrorResponse.create(AuthErrorStatus.TOKEN_EXPIRED));
    }
}
