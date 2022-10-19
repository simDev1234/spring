package com.example.demo.notice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.example.demo.notice.model.NoticeModel;

@RestController
public class ApiNoticeController {

    /**
     * 공지사항 조회
     */
    @GetMapping("/api/notice")
    public List<NoticeModel> notice(){

        List<NoticeModel> noticeModelList = new ArrayList<>();

        return noticeModelList;
    }

    /**
     * 공지사항 갯수
     */
    @GetMapping("/api/notice/count")
    public long noticeCount(){
        return 10;
    }

    /**
     * 공지사항 등록
     */
    @PostMapping("/api/notice")
    public NoticeModel addNotice(@RequestParam String title, @RequestParam String contents){
        return NoticeModel.builder()
                .id(1)
                .title(title)
                .contents(contents)
                .regDate(LocalDateTime.now())
                .build();
    }

}
