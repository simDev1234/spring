package com.example.fastlms.course.controller;

import com.example.fastlms.admin.service.CategoryService;
import com.example.fastlms.common.model.ResponseResult;
import com.example.fastlms.course.model.ServiceResult;
import com.example.fastlms.course.model.TakeCourseInput;
import com.example.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class ApiCourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;

    /* 수강 신청 */
    @PostMapping("/api/course/req.api")
    public ResponseEntity<?> courseReq(Model model,
                                       @RequestBody TakeCourseInput parameter,
                                       Principal principal) {

        parameter.setUserId(principal.getName()); // 로그인 Id 추가

        ServiceResult result = courseService.req(parameter); // 수강 신청

        if (!result.isResult()) {

            ResponseResult responseResult
                    = new ResponseResult(false, result.getMessage());

            return ResponseEntity.ok().body(responseResult);
        }

        return ResponseEntity.ok().body(true);
    }

}
