package com.catv.p2p.base.mapper;

import com.catv.p2p.base.domain.RealAuth;
import com.catv.p2p.base.query.RealAuthQueryObject;

import java.util.List;

public interface RealAuthMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RealAuth record);

    RealAuth selectByPrimaryKey(Long id);

    List<RealAuth> selectAll();

    int updateByPrimaryKey(RealAuth record);

    List<RealAuth> queryList(RealAuthQueryObject qo);

    Integer queryCount(RealAuthQueryObject qo);
}