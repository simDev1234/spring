package com.example.fastlms.course.service;

import com.example.fastlms.course.dto.TakeCourseDto;
import com.example.fastlms.course.model.TakeCourseParam;

import java.util.List;

public interface TakeCourseService {

    /* 강좌조회 */
    List<TakeCourseDto> list(TakeCourseParam parameter);

}
