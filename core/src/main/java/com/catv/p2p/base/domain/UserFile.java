package com.catv.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.HashMap;
import java.util.Map;

/**
 * 风控材料
 */
@Setter
@Getter
@Alias("UserFile")
public class UserFile extends BaseAuditDomain {

    private String image;// 风控材料图片;
    private SystemDictionaryItem fileType;// 风控材料分类
    private int score = 0;// 风控材料评分

    public String getJsonString(){
        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("id", id);
        jsonMap.put("applier", this.applier.getUsername());
        jsonMap.put("image",image);
        jsonMap.put("fileType",fileType.getTitle());
        return JSON.toJSONString(jsonMap);
    }
}
