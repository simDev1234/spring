package com.example.demo.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class NoticeModel {

    private long id;
    private String title;
    private String contents;
    private LocalDateTime regDate;

}
