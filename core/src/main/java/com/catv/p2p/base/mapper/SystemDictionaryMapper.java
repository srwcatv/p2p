package com.catv.p2p.base.mapper;

import com.catv.p2p.base.domain.SystemDictionary;
import com.catv.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemDictionary record);

    SystemDictionary selectByPrimaryKey(Long id);

    List<SystemDictionary> selectAll();

    int updateByPrimaryKey(SystemDictionary record);

    List<SystemDictionary> selectPageResult(SystemDictionaryQueryObject qo);

    Integer selectCount(SystemDictionaryQueryObject qo);

    List selectListBySn(String sn);
}