package com.cheolhyeon.communityapi.module.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class ErrorResponseBindingResult {
    private final String defaultMessage;
    private final String errorField;
    private final String rejectedValue;
    private final String errorAnnotation;

    public static List<ErrorResponseBindingResult> create(List<FieldError> errors) {
        return errors.stream()
                .map(fieldError -> new ErrorResponseBindingResult(
                        fieldError.getDefaultMessage(),
                        fieldError.getField(),
                        Objects.requireNonNull(fieldError.getRejectedValue()).toString(),
                        fieldError.getCode()
                ))
                .collect(Collectors.toList());
    }
}
