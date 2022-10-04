package com.example.fastlms.member.service;

import com.example.fastlms.member.model.MemberRegister;
import com.example.fastlms.member.model.MemberFindPassword;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    /**
     * 계정 생성
     * @param request
     */
    boolean register(MemberRegister.Request request);

    /**
     * uuid에 해당하는 계정 활성화
     * @param uuid
     */
    boolean emailAuth(String uuid);

    /**
     * 입력한 이메일로 비밀번호 초기화 정보 전송
     * @param request
     * @return
     */
    boolean sendResetPassword(MemberFindPassword.Request request);

    /**
     * 입력받은 uuid에 대해서 password로 초기화
     * @param resetPasswordKey
     * @param password
     * @return
     */
    boolean resetPassword(String resetPasswordKey, String password);

    /**
     * 입력받은 uuid 값이 유효한지 확인
     * @param resetPasswordKey
     * @return
     */
    boolean checkResetPassword(String resetPasswordKey);
}
