package com.example.fastlms.admin.controller;

import com.example.fastlms.admin.dto.MemberDto;
import com.example.fastlms.admin.model.MemberParam;
import com.example.fastlms.admin.util.PageUtil;
import com.example.fastlms.member.model.MemberStatusInput;
import com.example.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    /* 회원 목록 출력 */
    @GetMapping("/admin/member/list.do")
    public String list(Model model, MemberParam parameter){

        // 페이징 초기화 -- 회원 목록 최초 진입 시 1~10
        parameter.init();

        // 입력값에 따라 MyBatis 쿼리동작 및 조회 결과 반환
        // - 입력값 : 검색범위(검색옵션, 검색어), 현재페이지 시작점-끝점
        List<MemberDto> members = memberService.list(parameter);

        // 페이징 Util class를 통해 html pagination 반환
        long totalCount = 0; // 전체 row 갯수

        if (members != null && members.size() > 0) {
            totalCount = members.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();

        PageUtil pageUtil = new PageUtil(totalCount, parameter.getPageSize(),
                parameter.getPageIndex(), queryString);

        // Data-binding
        // - 전달값 : 현재페이지 회원목록,
        model.addAttribute("list", members);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pageUtil.pager());

        return "admin/member/list";

    }

    /* 회원 상세 정보 */
    @GetMapping("/admin/member/detail.do")
    public String detail(Model model, MemberParam parameter){

        MemberDto member = memberService.detail(parameter.getUserId());

        model.addAttribute("member", member);

        return "admin/member/detail";

    }

    /* 회원 상태 변경 */
    @PostMapping("/admin/member/status.do")
    public String status(Model model, MemberStatusInput param) {
        boolean result = memberService.updateStatus(
                param.getUserId() , param.getUserStatus());

        return "redirect:/admin/member/detail.do?userId=" + param.getUserId();
    }

    /* 회원 비밀번호 변경 */
    @PostMapping("/admin/member/password.do")
    public String password(Model model, MemberStatusInput param) {

        boolean result = memberService.updatePassword(
                param.getUserId(), param.getPassword());

        return "redirect:/admin/member/detail.do?userId=" + param.getUserId();
    }

}
