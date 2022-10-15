package com.example.fastlms.member.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginHistoryParam {

    private String userId;
    private String userAgent;
    private String userIp;

}
