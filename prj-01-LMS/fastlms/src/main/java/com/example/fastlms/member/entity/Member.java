package com.example.fastlms.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Member implements MemberCode{
    @Id
    private String userId;
    private String password;
    private String userName;
    private String phoneNumber;

    // 이메일 인증 여부 판단
    private boolean emailAuthYn;
    private String emailAuthKey;
    private LocalDateTime emailAuthAt;

    // 비밀번호 초기화 관련
    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitDate;

    // 일반적으로, 관리자 권한 및 회원 Role 지정 여부를 아래와 같이 확인
    // ㄴ Role : 준회원/정회원/특별회원/관리자
    // ㄴ ex. ROLE_SEMI_USER, ROLE_USER, ROLE_SPECIAL_USER, ROLE_ADMIN
    private boolean adminYn;

    // 회원 상태 : 이용가능상태, 정지상태
    private String userStatus; 

    @CreatedDate
    private LocalDateTime registeredAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
