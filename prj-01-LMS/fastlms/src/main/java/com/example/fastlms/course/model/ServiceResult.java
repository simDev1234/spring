package com.example.fastlms.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServiceResult {

    boolean result;
    String message;

    public ServiceResult(boolean result) {
        this.result = result;
    }

    public ServiceResult() {
        this.result = true;
    }
}
