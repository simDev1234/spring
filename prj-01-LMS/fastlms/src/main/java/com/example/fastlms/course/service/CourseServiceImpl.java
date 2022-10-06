package com.example.fastlms.course.service;

import com.example.fastlms.admin.dto.MemberDto;
import com.example.fastlms.course.dto.CourseDto;
import com.example.fastlms.course.entity.Course;
import com.example.fastlms.course.mapper.CourseMapper;
import com.example.fastlms.course.model.CourseInput;
import com.example.fastlms.course.model.CourseParam;
import com.example.fastlms.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    private LocalDate getLocalDate(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {

        }
        return null;

    }

    /**
     * 강좌 등록
     */
    @Override
    public boolean add(CourseInput parameter) {

        //2021-09-12
        //20210912
        LocalDate saleEndDt = getLocalDate(parameter.getSaleEndAtText());

        courseRepository.save(
                Course.builder()
                        .subject(parameter.getSubject())
                        .categoryId(parameter.getCategoryId())
                        .keyword(parameter.getKeyword())
                        .summary(parameter.getSummary())
                        .contents(parameter.getContents())
                        .price(parameter.getPrice())
                        .salePrice(parameter.getSalePrice())
                        .saleEndAt(saleEndDt)
                        .build()
        );

        return true;
    }

    /**
     * 강좌 수정
     */
    @Override
    public boolean set(CourseInput parameter) {

        Optional<Course> optionalCourse =
                courseRepository.findById(parameter.getId());

        if (!optionalCourse.isPresent()) {
            return false;
        }

        LocalDate saleEndDt = getLocalDate(parameter.getSaleEndAtText());

        Course course = optionalCourse.get();
        course.setSubject(parameter.getSubject());
        course.setCategoryId(parameter.getCategoryId());
        course.setKeyword(parameter.getKeyword());
        course.setSummary(parameter.getSummary());
        course.setContents(parameter.getContents());
        course.setPrice(parameter.getPrice());
        course.setSalePrice(parameter.getSalePrice());
        course.setSaleEndAt(saleEndDt);

        courseRepository.save(course);

        return true;
    }

    /**
     * 목록 조회
     */
    @Override
    public List<CourseDto> list(CourseParam parameter) {
        long totalCount = courseMapper.selectListCount(parameter);

        List<CourseDto> list = courseMapper.selectList(parameter);

        if (!CollectionUtils.isEmpty(list)){
            int i = 0;
            for(CourseDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }

        return list;
    }

    /**
     * 강좌 수정
     */
    @Override
    public CourseDto getById(long id) {

        return courseRepository.findById(id)
                .map(CourseDto::of).orElse(null);

    }

    /**
     * 강좌 삭제
     */
    @Override
    public boolean del(String idList) {

        if (idList != null && idList.length() > 0) {

            String[] ids = idList.split(",");

            for (String x : ids) {

                long id = 0L;
                try {
                   id = Long.parseLong(x);
                } catch (Exception e) {
                    return false;
                }

                if (id > 0) {
                    courseRepository.deleteById(id);
                }
            }

        }

        return true;
    }
}
