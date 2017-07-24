package com.catv.p2p.base.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据字典分类高级查询
 */
@Getter
@Setter
public class SystemDictionaryQueryObject extends QueryObject {

    private String keyword;
    private Long parentId;

    public String getKeyword(){
        return this.keyword == null?null:keyword;
    }
}
