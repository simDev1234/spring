package com.example.fastlms.member.service;

public interface LoginHistoryService {

    /**
     * 로그인 히스토리 저장
     */
    void saveLoginHistory(String userId, String userIp, String userAgent);

}
