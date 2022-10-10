package com.example.fastlms.course.controller;

import com.example.fastlms.util.PageUtil;

public class BaseController {

    public String getPagerHtml(long totalCount, long pageSize,
                               long pageIndex, String queryString){
        PageUtil pageUtil = new PageUtil(totalCount, pageSize,
                pageIndex, queryString);

        return pageUtil.pager();
    }

}
