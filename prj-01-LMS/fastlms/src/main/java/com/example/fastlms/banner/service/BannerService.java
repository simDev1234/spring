package com.example.fastlms.banner.service;

import com.example.fastlms.admin.dto.BannerDto;
import com.example.fastlms.admin.model.BannerParam;

import java.util.List;

public interface BannerService {

    void register(BannerDto build);

    List<BannerDto> list(BannerParam parameter);

    List<BannerDto> clientList();
}
