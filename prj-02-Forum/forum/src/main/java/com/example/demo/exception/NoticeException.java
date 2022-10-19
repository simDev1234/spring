package com.example.demo.exception;

import com.example.demo.type.ErrorCode;
import lombok.Getter;

@Getter
public class NoticeException extends RuntimeException {

    private final ErrorCode errorCode;

    public NoticeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public NoticeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public NoticeException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public NoticeException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public NoticeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }
}
