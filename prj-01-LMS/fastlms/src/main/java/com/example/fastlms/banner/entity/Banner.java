package com.example.fastlms.banner.entity;

import com.example.fastlms.admin.dto.BannerDto;
import com.example.fastlms.admin.model.CommonParam;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Banner extends CommonParam {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String localFilePath;
    private String urlFilePath;
    private String link;
    private String bannerType;
    private long bannerOrder;
    private boolean openYn;

    @CreatedDate
    private LocalDateTime registeredAt;

    public static Banner from(BannerDto dto) {
        return Banner.builder()
                .title(dto.getTitle())
                .localFilePath(dto.getLocalFilePath())
                .urlFilePath(dto.getUrlFilePath())
                .link(dto.getLink())
                .bannerType(dto.getBannerType())
                .bannerOrder(dto.getBannerOrder())
                .openYn(dto.isOpenYn())
                .registeredAt(dto.getRegisteredAt())
                .build();
    }

}


