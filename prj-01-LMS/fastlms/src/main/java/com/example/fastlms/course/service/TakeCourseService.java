package com.example.fastlms.course.service;

import com.example.fastlms.course.dto.TakeCourseDto;
import com.example.fastlms.course.model.ServiceResult;
import com.example.fastlms.course.model.TakeCourseParam;

import java.util.List;

public interface TakeCourseService {

    /* 강좌조회 */
    List<TakeCourseDto> list(TakeCourseParam parameter);
    
    /* 수강내용 상태 변경 */
    ServiceResult updateStatus(long id, String status);

}
