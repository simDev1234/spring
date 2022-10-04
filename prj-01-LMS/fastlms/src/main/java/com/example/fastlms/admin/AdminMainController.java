package com.example.fastlms.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMainController {

    /* 관리자 메인 page mapping */
    @GetMapping("/admin/main.do")
    public String main(){
        return "admin/main";
    }
}
