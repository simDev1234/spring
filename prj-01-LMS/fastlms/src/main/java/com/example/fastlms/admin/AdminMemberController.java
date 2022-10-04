package com.example.fastlms.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminMemberController {

    /* 회원 관리 page mapping */
    @GetMapping("/admin/member/list.do")
    public String list(){
        return "admin/member/list";
    }

    @RequestMapping("/error/denied")
    public String errorDenied() {
        return "error/denied";
    }

}
