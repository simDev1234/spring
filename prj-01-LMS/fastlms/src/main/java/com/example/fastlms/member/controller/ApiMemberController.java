package com.example.fastlms.member.controller;

import com.example.fastlms.common.model.ResponseResult;
import com.example.fastlms.course.dto.TakeCourseDto;
import com.example.fastlms.course.model.ServiceResult;
import com.example.fastlms.course.model.TakeCourseInput;
import com.example.fastlms.course.service.TakeCourseService;
import com.example.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiMemberController {

    private final MemberService memberService;
    private final TakeCourseService takeCourseService;

    /**
     * 수강 취소
     * - 해당 수강 신청 내역이 없는 경우,
     * - 로그인 상태가 아닌 경우,
     * - 해당 수강 신청이 내 수강 신청이 아닌 경우, 실패 응답
     */
    @PostMapping("/api/member/course/cancel.api")
    public ResponseEntity<?> memberTakeCourse(Principal principal, Model model,
                                              @RequestBody TakeCourseInput parameter){

        // 해당 수강 신청 내역이 있는지 확인
        TakeCourseDto detail =
                takeCourseService.detail(parameter.getTakeCourseId());

        if (detail == null) {
            ResponseResult responseResult = new ResponseResult(
                    false, "수강신청 정보가 존재하지 않습니다");
            return ResponseEntity.ok().body(responseResult);
        }

        // 내 수강 신청 내역이 맞는지 확인
        String userId = principal.getName();

        if (userId == null || !userId.equals(detail.getUserId())) {
            ResponseResult responseResult = new ResponseResult(
                    false, "본인의 수강신청 정보만 취소할 수 있습니다");
            return ResponseEntity.ok().body(responseResult);
        }

        // 취소하기
        ServiceResult serviceResult
                = takeCourseService.cancelCourse(parameter.getTakeCourseId());

        if (!serviceResult.isResult()) {
            ResponseResult responseResult = new ResponseResult(
                    false, serviceResult.getMessage());
            return ResponseEntity.ok().body(responseResult);
        }

        ResponseResult responseResult = new ResponseResult(true);
        return ResponseEntity.ok().body(responseResult);
    }

}
