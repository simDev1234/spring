package com.example.fastlms.course.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TakeCourseInput {

    private long courseId;
    private String userId;

}
