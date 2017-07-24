package com.catv.p2p.controller;

import com.catv.p2p.base.domain.RealAuth;
import com.catv.p2p.base.domain.UserInfo;
import com.catv.p2p.base.service.IRealAuthService;
import com.catv.p2p.base.service.IUserInfoService;
import com.catv.p2p.base.util.AjaxResult;
import com.catv.p2p.util.RequiredLogin;
import com.catv.p2p.util.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

/**
 * 实名认证相关
 */
@Controller
public class RealAuthController {

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IRealAuthService realAuthService;

    @Autowired
    private ServletContext servletContext;

    /**
     * 导向到实名认证页面
     * @param model
     * @return
     */
    @RequiredLogin
    @RequestMapping("/realAuth")
    public String realAuth(Model model){
        //得到当前登陆用户信息
        UserInfo current = userInfoService.getCurrent();
        //判断当前用户是否已经实名认证，如果已经认证导向到认证结果页面
        if (current.getIsRealAuth()){
            model.addAttribute("auditing",false);
            model.addAttribute("realAuth",realAuthService.query(current.getRealAuthId()));
            return "realAuth_result";
        } else {
            //如果没有认证，但是正在认证同样导向到结果页面，否则导向到认证页面进行认证
            if (current.getRealAuthId() != null) {
                model.addAttribute("auditing",true);
                return "realAuth_result";
            } else {
                return "realAuth";
            }
        }
    }

    /**
     * 证件照片上传
     * @param file 上传的文件
     */
    @RequestMapping("/realAuthUpload")
    @ResponseBody
    public String upload(MultipartFile file){
        //获得上下文路径
        String basePath = servletContext.getRealPath("/upload");
        //处理上传的文件
        String fileName = UploadFileUtils.getFileName(file,basePath);
        return "/upload/"+ fileName;
    }

    /**
     * 申请实名认证
     * @param realAuth
     * @return
     */
    @RequiredLogin
    @RequestMapping("/realAuth_save")
    @ResponseBody
    public AjaxResult realAuthSave(RealAuth realAuth){
        int ret = realAuthService.apply(realAuth);
        if (ret>0){
            return new AjaxResult("申请提交成功");
        } else {
            return new AjaxResult(false,"申请失败");
        }
    }
}
