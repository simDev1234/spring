package com.example.fastlms.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
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

}
