package com.example.demo.exception;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ErrorResponse {

    private String code;
    private String message;

}
