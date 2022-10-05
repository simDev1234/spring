package com.example.fastlms.member.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberStatusInput {
    private String userId;
    private String userStatus;
    private String password;
}
