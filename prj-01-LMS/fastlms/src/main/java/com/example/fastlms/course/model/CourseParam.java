package com.example.fastlms.course.model;

import com.example.fastlms.admin.model.CommonParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseParam extends CommonParam {

    long id;
    long categoryId;

}
