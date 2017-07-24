package com.catv.p2p.base.service.impl;

import com.catv.p2p.base.domain.SystemDictionary;
import com.catv.p2p.base.mapper.SystemDictionaryMapper;
import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.base.query.SystemDictionaryQueryObject;
import com.catv.p2p.base.service.ISystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典分类
 */
@Service
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {

    @Autowired
    SystemDictionaryMapper systemDictionaryMapper;

    public PageResult queryPageResult(SystemDictionaryQueryObject qo) {
        return new PageResult(systemDictionaryMapper.selectPageResult(qo)
                ,systemDictionaryMapper.selectCount(qo)
                ,qo.getCurrentPage()
                ,qo.getPageSize()
                );
    }

    public int update(SystemDictionary systemDictionary) {
        return systemDictionaryMapper.updateByPrimaryKey(systemDictionary);
    }

    public int save(SystemDictionary systemDictionary) {
        return systemDictionaryMapper.insert(systemDictionary);
    }

    public int delete(Long id) {
        return systemDictionaryMapper.deleteByPrimaryKey(id);
    }

    public List<SystemDictionary> getAll() {
        return systemDictionaryMapper.selectAll();
    }

    public List queryListBySn(String sn) {
        return systemDictionaryMapper.selectListBySn(sn);
    }
}
