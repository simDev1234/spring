package com.example.fastlms.member.model;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

public class MemberFindPassword {

    @Data
    @Builder
    public static class Request {
        @NotNull
        private String userId;
        @NotNull
        private String userName;
    }
}
