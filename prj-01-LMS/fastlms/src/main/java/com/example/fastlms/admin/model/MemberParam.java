package com.example.fastlms.admin.model;

import lombok.Builder;
import lombok.Data;

@Data
public class MemberParam {
    private long pageIndex;
    private long pageSize;
    private String searchType;
    private String searchValue;

    private String userId;

    /*
        limit pageStart, pageEnd
        limit 0, 10    --> pageIndex 1
        limit 10, 10   --> pageIndex 2
    */
    public long getPageStart(){
        init();
        return (pageIndex - 1) * pageSize;
    }

    public long getPageEnd() {
        init();
        return pageSize;
    }

    public void init() {
        if (pageIndex < 1) {
            pageIndex = 1;
        }

        if (pageSize < 10) {
            pageSize = 10;
        }
    }

    public String getQueryString() {
        init();

        StringBuilder sb = new StringBuilder();

        if (searchType != null && searchType.length() > 0) {
            sb.append(String.format("searchType=%s", searchType));
        }

        if (searchValue != null && searchValue.length() > 0) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("searchValue=%s", searchValue));
        }

        return sb.toString();
    }
}
