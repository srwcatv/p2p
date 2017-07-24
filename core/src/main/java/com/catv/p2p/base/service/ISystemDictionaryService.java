package com.catv.p2p.base.service;

import com.catv.p2p.base.domain.SystemDictionary;
import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

/**
 * 数据字典分类业务
 */
public interface ISystemDictionaryService {

    /**
     * 数据字典分类分页查询
     * @param qo 查询类
     * @return
     */
    PageResult queryPageResult(SystemDictionaryQueryObject qo);

    /**
     * 修改
     * @param systemDictionary
     * @return
     */
    int update(SystemDictionary systemDictionary);

    /**
     * 保存
     * @param systemDictionary
     * @return
     */
    int save(SystemDictionary systemDictionary);

    /**
     * 删除字典分类
     * @param id
     * @return
     */
    int delete(Long id);

    List<SystemDictionary> getAll();

    /**
     * 根据分类sn查字典明细
     * @param sn
     * @return
     */
    List queryListBySn(String sn);
}
