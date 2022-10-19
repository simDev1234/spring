package com.example.demo.config;

import com.example.demo.exception.ErrorResponse;
import com.example.demo.exception.NoticeException;
import com.example.demo.type.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoticeException.class)
    public ResponseEntity<ErrorResponse> noticeException(NoticeException e){

        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();

        return new ResponseEntity<ErrorResponse>(errorResponse, errorCode.getHttpStatus());
    }

}
