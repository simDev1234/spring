package com.example.fastlms.admin.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerDto {
    Long id;
    private String title;
    private String localFilePath;
    private String urlFilePath;
    private String link;
    private String bannerType;
    private long bannerOrder;
    private boolean openYn;
    long totalCount;
    long seq;
    private LocalDateTime registeredAt;

    public String getRegisteredAtText(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

        return registeredAt != null? registeredAt.format(formatter) : "";

    }

}
