package com.example.fastlms.banner.mapper;

import com.example.fastlms.admin.dto.BannerDto;
import com.example.fastlms.admin.model.BannerParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BannerMapper {

    List<BannerDto> selectList(BannerParam param);

    long selectListCount(BannerParam param);

    List<BannerDto> selectListInUse();
}
