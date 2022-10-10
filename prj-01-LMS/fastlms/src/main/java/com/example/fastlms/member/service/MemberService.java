package com.example.fastlms.member.service;

import com.example.fastlms.admin.dto.MemberDto;
import com.example.fastlms.admin.model.MemberParam;
import com.example.fastlms.course.model.ServiceResult;
import com.example.fastlms.member.model.MemberFindPassword;
import com.example.fastlms.member.model.MemberInput;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {

    /**
     * 계정 생성
     */
    boolean register(MemberInput.Request request);

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
    List<MemberDto> list(MemberParam request);

    /**
      회원 상세 정보
    * */
    MemberDto detail(String userId);

    /**
     * 회원 상태 변경
     */
    boolean updateStatus(String userId, String userStatus);

    /**
     * 회원 비밀번호 초기화
     */
    boolean updatePassword(String userId, String password);

    /**
     * 회원 정보 페이지 내 비밀번호 변경 가능
     */
    ServiceResult updateMemberPassword(MemberInput.Request parameter);

    /**
     * 회원 정보 수정
     */
    ServiceResult updateMember(MemberInput.Request parameter);

    /**
     * 회원 탈퇴
     */
    ServiceResult withdraw(String id, String password);
}
