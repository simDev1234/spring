package com.example.fastlms.admin.dto;

import com.example.fastlms.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MemberDto {

    String userId;
    String password;
    String userName;
    String phoneNumber;

    boolean emailAuthYn;
    String emailAuthKey;
    LocalDateTime emailAuthAt;

    String resetPasswordKey;
    LocalDateTime resetPasswordLimitDate;
    boolean adminYn;

    LocalDateTime registeredAt;
    LocalDateTime updatedAt;

    String userStatus;

    // 추가 칼럼
    long totalCount;
    long seq;

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                .phoneNumber(member.getPhoneNumber())
                .password(member.getPassword())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthAt(member.getEmailAuthAt())
                .emailAuthKey(member.getEmailAuthKey())
                .resetPasswordKey(member.getResetPasswordKey())
                .resetPasswordLimitDate(member.getResetPasswordLimitDate())
                .adminYn(member.isAdminYn())
                .registeredAt(member.getRegisteredAt())
                .updatedAt(member.getUpdatedAt())
                .userStatus(member.getUserStatus())
                .build();
    }

}
