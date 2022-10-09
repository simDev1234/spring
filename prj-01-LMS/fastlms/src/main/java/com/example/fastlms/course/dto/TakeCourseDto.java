package com.example.fastlms.course.dto;

import com.example.fastlms.course.entity.TakeCourse;
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

    public static TakeCourseDto of(TakeCourse takeCourse) {
        return TakeCourseDto.builder()
                .id(takeCourse.getId())
                .courseId(takeCourse.getCourseId())
                .userId(takeCourse.getUserId())
                .payPrice(takeCourse.getPayPrice())
                .status(takeCourse.getStatus())
                .registeredAt(takeCourse.getRegisteredAt())
                .build();
    }

    public String getRegisteredAtText(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

        return registeredAt != null? registeredAt.format(formatter) : "";

    }

}
