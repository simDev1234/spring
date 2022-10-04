package com.example.fastlms.member.service.impl;

import com.example.fastlms.admin.dto.MemberDto;
import com.example.fastlms.admin.mapper.MemberMapper;
import com.example.fastlms.admin.model.MemberSearch;
import com.example.fastlms.components.MailComponents;
import com.example.fastlms.member.entity.Member;
import com.example.fastlms.member.exception.MemberNotEmailAuthException;
import com.example.fastlms.member.model.MemberFindPassword;
import com.example.fastlms.member.model.MemberRegister;
import com.example.fastlms.member.repository.MemberRepository;
import com.example.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final MailComponents mailComponents;
    private final MemberMapper memberMapper;

    /**
     * 가입하기
     * - 동일한 아이디가 있는 경우 실패 응답
     * - 아이디(이메일) 등록 후 인증 메일 전송
     */
    @Override
    public boolean register(MemberRegister.Request request) {

        Optional<Member> optionalMember = memberRepository.findById(request.getUserId());

        if (optionalMember.isPresent()) {
            return false;
        }

        String encPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        String uuid = UUID.randomUUID().toString().replace("-", "");

        memberRepository.save(
                Member.builder()
                        .userId(request.getUserId())
                        .password(encPassword)
                        .userName(request.getUserName())
                        .phoneNumber(request.getPhoneNumber())
                        .emailAuthYn(false)
                        .emailAuthKey(uuid)
                        .build()
        );

        // 인증메일
        String email = request.getUserId();
        String subject = "fastlms 사이트 가입을 축하드립니다.";
        String text = "<div><p>fastlms 사이트 가입을 축하드립니다.</p>" +
                "<p>아래 링크를 클릭하시어, 가입을 완료하세요.</p>" +
                "<a href = 'http://localhost:8080/member/email-auth?id="
                + uuid + "'>링크</a></div>";
        mailComponents.sendMail(email, subject, text);

        return true;
    }

    /**
     * 계정 활성화
     * - 계정이 없는 경우, 이미 계정이 활성화된 경우 실패 응답
     * - 이메일에서 링크를 통해 인증 키(uuid) 전달 시, 
     *   회원 승인 상태 변경
     */
    @Override
    public boolean emailAuth(String uuid) {

        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);

        if (!optionalMember.isPresent()) {
            return false;
        }

        Member member = optionalMember.get();

        if (member.isEmailAuthYn()) {
            return false;
        }

        member.setEmailAuthYn(true);
        member.setEmailAuthAt(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }

    /**
     * LDAP 사용자 인증
     * - 회원 정보가 잘못된 경우, 메일이 미인증된 경우 실패 응답
     * - 시큐러티의 GrantedAuthority를 통해 사용자 권한/역할 추가
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findById(username);

        // validate
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        if (!member.isEmailAuthYn()) {
            throw new MemberNotEmailAuthException("이메일 활성화 이후 로그인 해주세요.");
        }

        // 사용자 권한, Role 추가
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (member.isAdminYn()) { // 관리자일 경우
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(member.getUserName(), member.getPassword(), grantedAuthorities);
    }

    /**
     * 회원 비밀번호 초기화
     * - 아이디(이메일)과 이름이 일치하지 않는 경우 실패 응답
     * - 이메일로 초기화 인증 메일 전송
     */
    @Override
    public boolean sendResetPassword(MemberFindPassword.Request request) {

        Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(
                request.getUserId(), request.getUserName());

        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 잘못되었거나 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        String uuid = UUID.randomUUID().toString().replace("-","");

        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDate(LocalDateTime.now().plusDays(1));
        memberRepository.save(member);

        // 초기화 인증 메일
        String email = request.getUserId();
        String subject = "[fastlms] 비밀번호 초기화 메일입니다.";
        String text = "<div><p>fastlms 비밀번호 초기화 메일입니다.</p>" +
                "<p>아래 링크를 클릭하시어, 비밀번호 초기화를 완료하세요.</p>" +
                "<a href = 'http://localhost:8080/member/reset/password?id="
                + uuid + "'>비밀번호 초기화 링크</a></div>";
        mailComponents.sendMail(email, subject, text);

        return true;
    }

    /**
     * 입력받은 uuid에 대해서 password로 초기화
     * @param resetPasswordKey
     * @param password
     * @return
     */
    @Override
    public boolean resetPassword(String resetPasswordKey, String password) {

        Optional<Member> optionalMember =
                memberRepository.findByResetPasswordKey(resetPasswordKey);

        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 잘못되었거나 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        // 초기화 날짜가 유효한지 체크
        if (member.getResetPasswordLimitDate() == null) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        if (member.getResetPasswordLimitDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDate(null);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean checkResetPassword(String resetPasswordKey) {

        Optional<Member> optionalMember =
                memberRepository.findByResetPasswordKey(resetPasswordKey);

        if (!optionalMember.isPresent()) {
            return false;
        }

        Member member = optionalMember.get();

        // 초기화 날짜가 유효한지 체크
        if (member.getResetPasswordLimitDate() == null) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        if (member.getResetPasswordLimitDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        return true;
    }

    @Override
    public List<MemberDto> list(MemberSearch.Request request) {

        List<MemberDto> list = memberMapper.selectList(request);

        return list;
    }
}
