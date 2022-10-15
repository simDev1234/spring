package com.example.fastlms.main;

import com.example.fastlms.admin.dto.BannerDto;
import com.example.fastlms.admin.model.BannerParam;
import com.example.fastlms.banner.service.BannerService;
import com.example.fastlms.course.controller.BaseController;
import com.example.fastlms.member.service.LoginHistoryService;
import com.example.fastlms.member.service.MemberService;
import com.example.fastlms.util.WebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController{

    private final LoginHistoryService loginHistoryService;
    private final MemberService memberService;
    private final BannerService bannerService;


    @RequestMapping(value = "/")
    public String index(BannerParam parameter, Model model){

        List<BannerDto> list = bannerService.list(parameter);

        model.addAttribute("list", list);

        return "index";
    }

    @RequestMapping(value = "/member/login-success")
    public String saveLoginHistory(HttpServletRequest request, Principal principal){

        String userId = principal.getName();
        String userIp = WebUtils.getClientIp(request);
        String userAgent = WebUtils.getUserAgent(request);;

        loginHistoryService.saveLoginHistory(userId, userIp, userAgent);

        return "redirect:/";
    }

}
