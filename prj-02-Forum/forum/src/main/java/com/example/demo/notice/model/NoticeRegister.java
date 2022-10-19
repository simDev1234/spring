package com.example.demo.notice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeRegister {
    private String title;
    private String contents;
}
