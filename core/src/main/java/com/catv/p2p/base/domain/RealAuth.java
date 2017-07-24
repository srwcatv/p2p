package com.catv.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证
 */
@Setter
@Getter
@Alias("RealAuth")
public class RealAuth extends BaseAuditDomain {

    public static final int SEX_MALE = 0;
    public static final int SEX_FEMALE = 1;

    private String realName;//真实姓名
    private int sex;//性别
    private String idNumber;//证件号
    private String bornDate;//出生日期
    private String address;//证件地址
    private String image1;//证件正面照片地址
    private String image2;//证件反面照片地址


    public String getSexDisplay() {
        return sex == SEX_MALE ? "男性" : "女性";
    }

    public String getJsonString(){
        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("id", id);
        jsonMap.put("applier", this.applier.getUsername());
        jsonMap.put("realName", realName);
        jsonMap.put("idNumber", idNumber);
        jsonMap.put("sex", getSexDisplay());
        jsonMap.put("bornDate", bornDate);
        jsonMap.put("address", address);
        jsonMap.put("image1", image1);
        jsonMap.put("image2", image2);
        return JSON.toJSONString(jsonMap);
    }

    /**
     * 获取用户真实名字的隐藏字符串，只显示姓氏
     *
     * @param realName
     *            真实名字
     * @return
     */
    public String getAnonymousRealName() {
        if (StringUtils.hasLength(this.realName)) {
            int len = realName.length();
            String replace = "";
            replace += realName.charAt(0);
            for (int i = 1; i < len; i++) {
                replace += "*";
            }
            return replace;
        }
        return realName;
    }

    /**
     * 获取用户身份号码的隐藏字符串
     *
     * @param idNumber
     * @return
     */
    public String getAnonymousIdNumber() {
        if (StringUtils.hasLength(idNumber)) {
            int len = idNumber.length();
            String replace = "";
            for (int i = 0; i < len; i++) {
                if ((i > 5 && i < 10) || (i > len - 5)) {
                    replace += "*";
                } else {
                    replace += idNumber.charAt(i);
                }
            }
            return replace;
        }
        return idNumber;
    }

    /**
     * 获取用户住址的隐藏字符串
     *
     * @param currentAddress
     *            用户住址
     * @return
     */
    public String getAnonymousAddress() {
        if (StringUtils.hasLength(address) && address.length() > 4) {
            String last = address.substring(address.length() - 4,
                    address.length());
            String stars = "";
            for (int i = 0; i < address.length() - 4; i++) {
                stars += "*";
            }
            return stars + last;
        }
        return address;
    }
}
