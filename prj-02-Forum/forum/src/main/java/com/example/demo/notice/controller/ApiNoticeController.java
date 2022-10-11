package com.example.demo.notice.controller;

import com.example.demo.notice.model.NoticeModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ApiNoticeController {

    @GetMapping("/api/notice")
    public List<NoticeModel> notice(){

        List<NoticeModel> noticeModelList = new ArrayList<>();

        return noticeModelList;
    }

    @GetMapping("/api/notice/count")
    public long noticeCount(){

        return 10;
    }

}
