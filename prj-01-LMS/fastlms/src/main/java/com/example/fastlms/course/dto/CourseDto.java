package com.example.fastlms.course.dto;

import com.example.fastlms.course.entity.Course;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private Long id;
    private long categoryId;

    private String imagePath;
    private String keyword;
    private String subject;

    private String summary;

    private String contents;
    private long price;
    private long salePrice;
    private LocalDate saleEndAt;

    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;

    private long totalCount;
    private long seq;

    public static CourseDto of(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .categoryId(course.getCategoryId())
                .imagePath(course.getImagePath())
                .keyword(course.getKeyword())
                .subject(course.getSubject())
                .summary(course.getSummary())
                .contents(course.getContents())
                .price(course.getPrice())
                .salePrice(course.getPrice())
                .saleEndAt(course.getSaleEndAt())
                .registeredAt(course.getRegisteredAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }
}
