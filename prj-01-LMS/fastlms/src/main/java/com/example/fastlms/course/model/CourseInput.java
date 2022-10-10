package com.example.fastlms.course.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseInput {

    private long id;
    private long categoryId;
    private String subject;
    private String keyword;
    private String summary;
    private String contents;
    private long price;
    private long salePrice;
    private String saleEndAtText;

    // 삭제를 위한
    String idList;

    // 파일 업로드
    String saveFilename;
    String urlFilename;

}
