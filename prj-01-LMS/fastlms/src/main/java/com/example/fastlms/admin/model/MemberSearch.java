package com.example.fastlms.admin.model;

import lombok.Builder;
import lombok.Data;

public class MemberSearch {
    @Data
    @Builder
    public static class Request{
        private String searchType;
        private String searchValue;
    }

}
