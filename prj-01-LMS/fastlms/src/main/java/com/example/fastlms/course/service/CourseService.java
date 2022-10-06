package com.example.fastlms.course.service;

import com.example.fastlms.course.dto.CourseDto;
import com.example.fastlms.course.model.CourseInput;
import com.example.fastlms.course.model.CourseParam;

import java.util.List;

public interface CourseService {

    /* 강좌등록 */
    boolean add(CourseInput parameter);

    /* 강좌조회 */
    List<CourseDto> list(CourseParam parameter);

    /* 강좌상세정보 */
    CourseDto getById(long id);

    /* 강좌정보수정 */
    boolean set(CourseInput parameter);

    /* 강좌내용삭제 */
    boolean del(String idList);
}
