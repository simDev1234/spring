package com.example.fastlms.member.service.impl;

import com.example.fastlms.member.entity.LoginHistory;
import com.example.fastlms.member.entity.Member;
import com.example.fastlms.member.model.LoginHistoryParam;
import com.example.fastlms.member.repository.LoginHistoryRepository;
import com.example.fastlms.member.repository.MemberRepository;
import com.example.fastlms.member.service.LoginHistoryService;
import com.example.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginHistoryImpl implements LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;
    private final MemberRepository memberRepository;

    @Override
    public void saveLoginHistory(String userId, String userIp, String userAgent) {

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 정보 없음"));

        LoginHistory loginHistory = LoginHistory.builder()
                                                .userId(userId)
                                                .userIp(userIp)
                                                .userAgent(userAgent)
                                                .lastLoginDate(LocalDateTime.now())
                                                .build();

        member.setUserIp(userIp);
        member.setUserAgent(userAgent);
        member.setLastLoginDate(loginHistory.getLastLoginDate());
        memberRepository.save(member);

        loginHistoryRepository.save(loginHistory);

    }
}
