package com.catv.p2p.base.mapper;

import com.catv.p2p.base.domain.UserInfo;
import java.util.List;
import java.util.Map;

public interface UserInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    List<UserInfo> selectAll();

    int updateByPrimaryKey(UserInfo record);

    int selectByPhoneNumber(String phoneNumber);

    int selectByEmail(String email);

    List<Map<String,Object>> getList(String keyword);
}