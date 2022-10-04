package com.example.fastlms.admin;

import com.example.fastlms.admin.dto.MemberDto;
import com.example.fastlms.admin.model.MemberSearch;
import com.example.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    /* 회원 목록 출력 */
    @GetMapping("/admin/member/list.do")
    public String list(Model model, MemberSearch.Request request){

        List<MemberDto> members = memberService.list(request);

        model.addAttribute("list", members);

        return "admin/member/list";

    }

}
