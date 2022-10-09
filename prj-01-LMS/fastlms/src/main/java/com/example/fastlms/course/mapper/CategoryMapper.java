package com.example.fastlms.course.mapper;

import com.example.fastlms.admin.dto.CategoryDto;
import com.example.fastlms.course.model.CourseParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<CategoryDto> select(CourseParam parameter);

}
