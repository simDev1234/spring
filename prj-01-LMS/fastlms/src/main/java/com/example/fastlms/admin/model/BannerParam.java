package com.example.fastlms.admin.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerParam extends CommonParam{
    private String title;
    private String localFilePath;
    private String urlFilePath;
    private String link;
    private String bannerType;
    private long bannerOrder;
    private boolean openYn;
}
