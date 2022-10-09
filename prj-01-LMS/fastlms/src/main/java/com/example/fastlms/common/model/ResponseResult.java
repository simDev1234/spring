package com.example.fastlms.common.model;

import lombok.*;

@Getter
@Setter
public class ResponseResult {

    private ResponseResultHeader header;
    private Object body;

    public ResponseResult(boolean result, String message) {
        header = new ResponseResultHeader(result, message);
    }

    public ResponseResult(boolean result) {
        header = new ResponseResultHeader(result);
    }
}
