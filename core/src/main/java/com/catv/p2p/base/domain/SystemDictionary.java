package com.catv.p2p.base.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典分类
 */
@Getter
@Setter
@Alias("SystemDictionary")
public class SystemDictionary extends BaseDomain {

    private String sn;//分类编码
    private String title;//分类标题
    private String intro;//分类使用说明

    /**
     * 返回当前的json字符串
     * @return
     */
    public String getJsonString(){
        Map<String,Object> json=new HashMap<>();
        json.put("id",id);
        json.put("sn", sn);
        json.put("title", title);
        return JSONObject.toJSONString(json);
    }
}
