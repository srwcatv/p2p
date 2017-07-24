package com.catv.p2p.base.service.impl;

import com.catv.p2p.base.domain.SystemDictionaryItem;
import com.catv.p2p.base.mapper.SystemDictionaryItemMapper;
import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.base.query.SystemDictionaryQueryObject;
import com.catv.p2p.base.service.ISystemDictionaryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据字典分类
 */
@Service
public class SystemDictionaryItemServiceImpl implements ISystemDictionaryItemService {

    @Autowired
    SystemDictionaryItemMapper systemDictionaryItemMapper;

    public PageResult queryPageResult(SystemDictionaryQueryObject qo) {
        return new PageResult(systemDictionaryItemMapper.selectPageResult(qo)
                ,systemDictionaryItemMapper.selectCount(qo)
                ,qo.getCurrentPage()
                ,qo.getPageSize()
                );
    }

    public int update(SystemDictionaryItem systemDictionaryItem) {
        return systemDictionaryItemMapper.updateByPrimaryKey(systemDictionaryItem);
    }

    public int save(SystemDictionaryItem systemDictionaryItem) {
        return systemDictionaryItemMapper.insert(systemDictionaryItem);
    }

    public int delete(Long id) {
        return systemDictionaryItemMapper.deleteByPrimaryKey(id);
    }

    public SystemDictionaryItem queryById(Long id) {
        return systemDictionaryItemMapper.selectByPrimaryKey(id);
    }
}
