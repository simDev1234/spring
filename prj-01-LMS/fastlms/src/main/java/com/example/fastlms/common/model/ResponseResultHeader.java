package com.example.fastlms.common.model;

import lombok.*;

@Getter
@Setter
public class ResponseResultHeader {

    private boolean result;
    private String message;

    public ResponseResultHeader(boolean result) {
        this.result = result;
    }

    public ResponseResultHeader(boolean result, String message) {
        this.result = result;
        this.message = message;
    }
}
