package com.example.fastlms.member.controller;

import com.example.fastlms.member.model.MemberRegister;
import com.example.fastlms.member.model.MemberFindPassword;
import com.example.fastlms.member.model.MemberResetPassword;
import com.example.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /* 로그인 page mapping  */
    @RequestMapping("/member/login")
    public String login() {
        return "member/login";
    }

    /* 회원가입 page mapping  */
    @GetMapping("/member/register")
    public String register() {
        return "member/register";
    }

    /* 회원 등록 처리 */
    @PostMapping(value = "/member/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String registerSubmit(MemberRegister.Request request, Model model) {

        boolean result = memberService.register(request);

        model.addAttribute("result", result);

        return "member/register-complete";
    }

    /* 회원 인증 처리 (메일 인증) */
    @GetMapping("/member/email-auth")
    public String emailAuth(@RequestParam String id, Model model) {

        boolean result = memberService.emailAuth(id);

        model.addAttribute("result", result);

        return "member/email-auth";
    }

    /* 비밀번호 초기화 page mapping  */
    @GetMapping("/member/find/password")
    public String findPassword() {
        return "/member/find-password";
    }

    /* 비밀번호 초기화 정보 입력 및 인증 메일 전송 */
    @PostMapping("/member/find/password")
    public String findPasswordSubmit(MemberFindPassword.Request request, Model model){

        boolean result = false;
        try{
            result = memberService.sendResetPassword(request);
        } catch (Exception e) {

        }

        model.addAttribute("result", result);

        /*return "redirect:/";*/
        return "member/find-password-result";

    }

    /* 비밀번호 초기화 page mapping */
    @GetMapping("/member/reset/password")
    public String resetPassword(@RequestParam(name = "id")
                                    String resetPasswordKey, Model model){

        boolean result = memberService.checkResetPassword(resetPasswordKey);

        model.addAttribute("result", result);

        return "/member/reset-password";

    }

    /* 회원 비밀번호 초기화 처리 */
    @PostMapping("/member/reset/password")
    public String resetPasswordSubmit(MemberResetPassword.Request request, Model model){

        boolean result = false;

        try{
            result = memberService.resetPassword(request.getResetPasswordKey(), request.getPassword());
        } catch (Exception e) {

        }

        model.addAttribute("result", result);

        return "member/reset-password-result";
    }

    /* 회원 정보 page mapping */
    @GetMapping("/member/info")
    public String memberInfo(){
        return "member/member-info";
    }



}
