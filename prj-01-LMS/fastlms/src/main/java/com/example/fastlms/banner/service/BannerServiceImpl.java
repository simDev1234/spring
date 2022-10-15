package com.example.fastlms.banner.service;

import com.example.fastlms.admin.dto.BannerDto;
import com.example.fastlms.admin.dto.MemberDto;
import com.example.fastlms.admin.model.BannerParam;
import com.example.fastlms.banner.entity.Banner;
import com.example.fastlms.banner.mapper.BannerMapper;
import com.example.fastlms.banner.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;

    @Override
    public void register(BannerDto dto) {

        bannerRepository.save(
            Banner.from(dto)
        );
    }

    // 사용자단 목록
    @Override
    public List<BannerDto> clientList() {

        return bannerMapper.selectListInUse();
    }

    // 관리자단 목록
    @Override
    public List<BannerDto> list(BannerParam parameter) {

        long totalCount = bannerMapper.selectListCount(parameter);

        List<BannerDto> list = bannerMapper.selectList(parameter);

        if (!CollectionUtils.isEmpty(list)){
            int i = 0;
            for(BannerDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }

        return list;
    }
}
