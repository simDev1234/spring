package com.example.fastlms.admin.dto;

import com.example.fastlms.member.entity.LoginHistory;
import com.example.fastlms.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    // 우편 번호
    private String zipcode;
    private String addr;
    private String addrDetail;

    // 추가 칼럼
    long totalCount;
    long seq;

    // 관리자단 사용자 정보 추가
    LocalDateTime lastLoginDate;
    List<LoginHistory> loginHistories;

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
                .zipcode(member.getZipcode())
                .addr(member.getAddr())
                .addrDetail(member.getAddrDetail())
                .lastLoginDate(member.getLastLoginDate())
                .build();
    }

    public String getRegisteredAtText(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        return registeredAt != null? registeredAt.format(formatter) : "";

    }

    public String getUpdatedAtText(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        return updatedAt != null? updatedAt.format(formatter) : "";

    }

}
