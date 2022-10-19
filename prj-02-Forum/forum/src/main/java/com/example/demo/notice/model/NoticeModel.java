package com.example.demo.notice.model;

import lombok.*;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeModel {

    private long id;
    private String title;
    private String contents;
    private LocalDateTime regDate;

}
