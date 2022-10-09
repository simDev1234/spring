package com.example.fastlms.member.model;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

public class MemberInput {

    @Data
    @Builder
    public static class Request {
        private String userId;
        private String password;
        private String userName;
        private String phoneNumber;
        private String newPassword;
        private String zipcode;
        private String addr;
        private String addrDetail;
    }
}
