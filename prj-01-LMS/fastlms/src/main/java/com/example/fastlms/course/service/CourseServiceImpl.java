package com.example.fastlms.course.service;

import com.example.fastlms.course.dto.CourseDto;
import com.example.fastlms.course.entity.Course;
import com.example.fastlms.course.entity.TakeCourse;
import com.example.fastlms.course.entity.TakeCourseCode;
import com.example.fastlms.course.mapper.CourseMapper;
import com.example.fastlms.course.model.CourseInput;
import com.example.fastlms.course.model.CourseParam;
import com.example.fastlms.course.model.ServiceResult;
import com.example.fastlms.course.model.TakeCourseInput;
import com.example.fastlms.course.repository.CourseRepository;
import com.example.fastlms.course.repository.TakeCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final TakeCourseRepository takeCourseRepository;

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

                long id;
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

    /**
     * 프론트 강좌 조회
     * - 전체 또는 특정 카테고리의 강좌 데이터 조회
     * @param parameter
     * @return
     */
    @Override
    public List<CourseDto> frontList(CourseParam parameter) {

        // 전체 카테고리
        if (parameter.getCategoryId() == 0) {
            return CourseDto.of(courseRepository.findAll());
        }

        // 특정 카테고리가 선택된 경우
        Optional<List<Course>> optionalCourseList =
                courseRepository.findByCategoryId(parameter.getCategoryId());

        if (optionalCourseList.isPresent()) {
            return CourseDto.of(optionalCourseList.get());
        }

        return null;
    }

    /**
     * 프론트 강좌 상세 정보 조회
     * - 해당 강좌 id의 상세데이터 조회
     * @param id
     */
    @Override
    public CourseDto frontDetail(long id) {
        return courseRepository.findById(id)
                .map(course -> CourseDto.of(course)).orElse(null);
    }

    /**
     * 수강 신청
     * - 수강 신청하려는 강좌가 없거나, 수강 신청을 이미 한 경우, 실패응답
     * - 수강 신청하려는 강좌id을 포함하여 수강 신청 정보 업데이트
     * @param parameter
     * @return
     */
    @Override
    public ServiceResult req(TakeCourseInput parameter) {

        ServiceResult result = new ServiceResult();

        Optional<Course> optionalCourse
                = courseRepository.findById(parameter.getCourseId());

        if (!optionalCourse.isPresent()) {
            result.setResult(false);
            result.setMessage("강좌 정보가 존재하지 않습니다.");
            return result;
        }

        Course course = optionalCourse.get();

        // 이미 신청 한 경우
        String[] statusList = {TakeCourseCode.STATUS_REQ, TakeCourseCode.STATUS_COMPLETE};

        long count = takeCourseRepository.countByCourseIdAndUserIdAndStatusIn(
                course.getId(), parameter.getUserId(), Arrays.asList(statusList)
        );

        if (count > 0) {
            result.setResult(false);
            result.setMessage("이미 신청한 강좌 정보가 존재합니다.");
            return result;
        }

        takeCourseRepository.save(
                TakeCourse.builder()
                        .courseId(course.getId())
                        .userId(parameter.getUserId())
                        .payPrice(course.getSalePrice())
                        .status(TakeCourseCode.STATUS_REQ)
                        .build()
        );

        result.setResult(true);
        result.setMessage("");
        return result;
    }
}
