package com.example.fastlms.admin.mapper;

import com.example.fastlms.admin.dto.MemberDto;
import com.example.fastlms.admin.model.MemberSearch;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MemberMapper {
    List<MemberDto> selectList(MemberSearch.Request request);
}
