package com.example.fastlms.course.model;

import com.example.fastlms.admin.model.CommonParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TakeCourseParam extends CommonParam {

    private long id;
    private String status;

}
