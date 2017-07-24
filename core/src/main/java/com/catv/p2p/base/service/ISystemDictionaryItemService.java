package com.catv.p2p.base.service;

import com.catv.p2p.base.domain.SystemDictionaryItem;
import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.base.query.SystemDictionaryQueryObject;

/**
 * 数据字典明细业务
 */
public interface ISystemDictionaryItemService {

    PageResult queryPageResult(SystemDictionaryQueryObject qo);

    /**
     * 修改
     * @param systemDictionaryItem
     * @return
     */
    int update(SystemDictionaryItem systemDictionaryItem);

    /**
     * 保存
     * @param systemDictionaryItem
     * @return
     */
    int save(SystemDictionaryItem systemDictionaryItem);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Long id);

    SystemDictionaryItem queryById(Long aLong);
}
