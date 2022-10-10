package com.example.fastlms.admin.controller;

import com.example.fastlms.admin.dto.CategoryDto;
import com.example.fastlms.admin.dto.MemberDto;
import com.example.fastlms.admin.model.MemberParam;
import com.example.fastlms.admin.service.CategoryService;
import com.example.fastlms.course.controller.BaseController;
import com.example.fastlms.course.dto.CourseDto;
import com.example.fastlms.course.dto.TakeCourseDto;
import com.example.fastlms.course.model.CourseParam;
import com.example.fastlms.course.model.ServiceResult;
import com.example.fastlms.course.model.TakeCourseParam;
import com.example.fastlms.course.service.CourseService;
import com.example.fastlms.course.service.TakeCourseService;
import com.example.fastlms.member.model.MemberStatusInput;
import com.example.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminTakeCourseController extends BaseController {

    private final TakeCourseService takeCourseService;
    private final CourseService courseService;

    /* 강좌 목록 */
    @GetMapping("/admin/takecourse/list.do")
    public String list(Model model, TakeCourseParam parameter,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
        }

        // 페이징 초기화 -- 회원 목록 최초 진입 시 1~10
        parameter.init();

        List<TakeCourseDto> takeCourseDtos = takeCourseService.list(parameter);

        // 페이징 Util class를 통해 html pagination 반환
        long totalCount = 0; // 전체 row 갯수

        if (!CollectionUtils.isEmpty(takeCourseDtos)) {
            totalCount = takeCourseDtos.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();

        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(),
                parameter.getPageIndex(), queryString);

        model.addAttribute("list", takeCourseDtos);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        List<CourseDto> courseList = courseService.courseListAll();
        model.addAttribute("courseList", courseList);

        return "admin/takecourse/list";
    }

    /* 수강 상태 변경 */
    @PostMapping("/admin/takecourse/status.do")
    public String status(Model model, TakeCourseParam parameter) {

        ServiceResult result = takeCourseService.updateStatus(
                parameter.getId(), parameter.getStatus());

        if (!result.isResult()){
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/admin/takecourse/list.do";
    }

}
