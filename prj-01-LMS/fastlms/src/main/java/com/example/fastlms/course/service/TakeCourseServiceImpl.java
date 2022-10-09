package com.example.fastlms.course.service;

import com.example.fastlms.course.dto.TakeCourseDto;
import com.example.fastlms.course.mapper.TakeCourseMapper;
import com.example.fastlms.course.model.TakeCourseParam;
import com.example.fastlms.course.repository.CourseRepository;
import com.example.fastlms.course.repository.TakeCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TakeCourseServiceImpl implements TakeCourseService{

    private final CourseRepository courseRepository;
    private final TakeCourseMapper takeCourseMapper;
    private final TakeCourseRepository takeCourseRepository;

    /**
     * 목록 조회
     */
    @Override
    public List<TakeCourseDto> list(TakeCourseParam parameter) {
        long totalCount = takeCourseMapper.selectListCount(parameter);

        List<TakeCourseDto> list = takeCourseMapper.selectList(parameter);

        if (!CollectionUtils.isEmpty(list)){
            int i = 0;
            for(TakeCourseDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }

        return list;
    }

}
