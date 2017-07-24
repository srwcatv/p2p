package com.catv.p2p.base.mapper;

import com.catv.p2p.base.domain.SystemDictionaryItem;
import com.catv.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    List<SystemDictionaryItem> selectAll();

    int updateByPrimaryKey(SystemDictionaryItem record);

    List<SystemDictionaryItem> selectPageResult(SystemDictionaryQueryObject qo);

    Integer selectCount(SystemDictionaryQueryObject qo);
}