package com.example.fastlms.member.service;

import com.example.fastlms.admin.dto.MemberDto;
import com.example.fastlms.member.entity.Member;
import com.example.fastlms.member.model.MemberRegister;
import com.example.fastlms.member.model.MemberFindPassword;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {

    /**
     * 계정 생성
     */
    boolean register(MemberRegister.Request request);

    /**
     * uuid에 해당하는 계정 활성화
     */
    boolean emailAuth(String uuid);

    /**
     * 입력한 이메일로 비밀번호 초기화 정보 전송
     */
    boolean sendResetPassword(MemberFindPassword.Request request);

    /**
     * 입력받은 uuid에 대해서 password로 초기화
     */
    boolean resetPassword(String resetPasswordKey, String password);

    /**
     * 입력받은 uuid 값이 유효한지 확인
     */
    boolean checkResetPassword(String resetPasswordKey);

    /**
     * 회원 목록 리턴(관리자에서만 사용 가능)
     */
    List<MemberDto> list();
}
