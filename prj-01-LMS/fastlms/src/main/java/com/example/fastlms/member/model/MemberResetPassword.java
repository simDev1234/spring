package com.example.fastlms.member.model;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

public class MemberResetPassword {

    @Data
    @Builder
    public static class Request {
        @NotNull
        private String userId;
        @NotNull
        private String userName;
        @NotNull
        private String password;
        @NotNull
        private String resetPasswordKey;
    }
}
