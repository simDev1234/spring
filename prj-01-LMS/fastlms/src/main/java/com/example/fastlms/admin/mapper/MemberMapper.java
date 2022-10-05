package com.example.fastlms.admin.mapper;

import com.example.fastlms.admin.dto.MemberDto;
import com.example.fastlms.admin.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MemberMapper {
    List<MemberDto> selectList(MemberParam parameter);

    long selectListCount(MemberParam parameter);
}
