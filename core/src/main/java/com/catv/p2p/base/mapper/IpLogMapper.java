package com.catv.p2p.base.mapper;

import com.catv.p2p.base.domain.IpLog;
import com.catv.p2p.base.query.IpLogQueryObject;

import java.util.List;

public interface IpLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IpLog record);

    IpLog selectByPrimaryKey(Long id);

    List<IpLog> selectAll();

    int updateByPrimaryKey(IpLog record);

    List<IpLog> selectPageResultByQo(IpLogQueryObject qo);

    int selectCount(IpLogQueryObject qo);
}