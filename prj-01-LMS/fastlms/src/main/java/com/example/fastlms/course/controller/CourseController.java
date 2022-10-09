package com.example.fastlms.course.controller;

import com.example.fastlms.admin.dto.CategoryDto;
import com.example.fastlms.admin.service.CategoryService;
import com.example.fastlms.course.dto.CourseDto;
import com.example.fastlms.course.model.CourseParam;
import com.example.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;

    /* 강좌 목록 */
    @GetMapping("/course")
    public String list(Model model, CourseParam parameter) {

        // 카테고리 목록
        List<CategoryDto> categoryList = categoryService.frontList(parameter);
        model.addAttribute("categoryList", categoryList);

        // 카테고리의 강좌 갯수 총합
        long totalCategoryCount = 0L;
        if (categoryList != null) {
            totalCategoryCount = categoryList.stream()
                    .mapToLong(categoryDto -> categoryDto.getCourseCount())
                    .sum();
        }

        model.addAttribute("totalCategoryCount", totalCategoryCount);

        // 강좌 목록
        List<CourseDto> list = courseService.frontList(parameter);
        model.addAttribute("list", list);

        return "course/index";
    }

    /* 강좌 상세 페이지 */
    @GetMapping("/course/{id}")
    public String detail(Model model, CourseParam parameter) {

        CourseDto detail = courseService.frontDetail(parameter.getId());
        model.addAttribute("detail",detail);

        return "course/detail";
    }

}
