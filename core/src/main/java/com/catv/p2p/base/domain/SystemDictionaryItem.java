package com.catv.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典明细
 */
@Getter
@Setter
@Alias("SystemDictionaryItem")
public class SystemDictionaryItem extends BaseDomain {

    private Long parentId;//分类id
    private String title;//显示名称
    private String intro;//使用说明
    private String tvalue;//可选值
    private Integer sequence;//分类中的排序

    public String getJsonString(){
        Map<String ,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("parentId",parentId);
        map.put("title",title);
        map.put("intro",intro);
        map.put("tvalue",tvalue);
        map.put("sequence",sequence);
        return JSON.toJSONString(map);
    }

}
