package com.example.demo.exception;

import lombok.Builder;
import lombok.Value;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Builder
@Value
public class ErrorResponse {

    private String code;
    private String message;

    public static ErrorResponse of(FieldError e) {
        return ErrorResponse.builder()
            .code(e.getField())
            .message(e.getDefaultMessage())
            .build();
    }
}
