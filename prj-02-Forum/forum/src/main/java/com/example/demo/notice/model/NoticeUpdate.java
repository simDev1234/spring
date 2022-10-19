package com.example.demo.notice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeUpdate {
    private Long id;
    private String title;
    private String contents;
}
