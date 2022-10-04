package com.example.fastlms.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminMainController {

    /* 접근 권한 불허 시 denied page mapping */
    @RequestMapping("/error/denied")
    public String errorDenied() {
        return "error/denied";
    }

    /* 관리자 메인 page mapping */
    @GetMapping("/admin/main.do")
    public String main(){
        return "admin/main";
    }
}
