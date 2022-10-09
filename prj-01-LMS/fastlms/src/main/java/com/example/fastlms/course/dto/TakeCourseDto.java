package com.example.fastlms.course.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TakeCourseDto {

    private Long id;

    private long courseId;
    private String userId;
    private String phoneNumber;

    private long payPrice; // 결제금액
    private String status; // 상태(수강신청, 결재완료, 수강취소)
    private LocalDateTime registeredAt; // 신청일

    private long totalCount;
    private long seq;

    // join
    String userName;
    String phone;
    String subject;

    public String getRegisteredAtText(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

        return registeredAt != null? registeredAt.format(formatter) : "";

    }

}
