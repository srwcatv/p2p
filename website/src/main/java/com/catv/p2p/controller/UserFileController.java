package com.catv.p2p.controller;

import com.catv.p2p.base.domain.UserFile;
import com.catv.p2p.base.service.ISystemDictionaryService;
import com.catv.p2p.base.service.IUserFileService;
import com.catv.p2p.base.util.AjaxResult;
import com.catv.p2p.base.util.UserContext;
import com.catv.p2p.util.RequiredLogin;
import com.catv.p2p.util.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 风控资料相关
 */
@Controller
public class UserFileController {

    @Autowired
    ServletContext servletContext;

    @Autowired
    IUserFileService userFileService;

    @Autowired
    ISystemDictionaryService systemDictionaryService;

    /**
     * 风控资料上传列表
     * @param model
     * @param request
     * @return
     */
    @RequiredLogin
    @RequestMapping("/userFile")
    public String userFile(Model model, HttpServletRequest request){
        List<UserFile> userFiles = userFileService.getByApplierIdAndHasFileType(UserContext.getCurrent().getId(),false);
        if (userFiles.size() > 0) {
            model.addAttribute("userFiles", userFiles);
            model.addAttribute("fileTypes",systemDictionaryService.queryListBySn("userFileType"));
            return "userFiles_commit";
        } else {
            userFiles = userFileService.getByApplierIdAndHasFileType(UserContext.getCurrent().getId(),true);
            model.addAttribute("sessionId",request.getSession().getId());
            model.addAttribute("userFiles", userFiles);
            return "userFiles";
        }
    }

    /**
     * 保存上传的风控资料
     * @param file
     */
    @RequestMapping("/uploadUserFile")
    @ResponseBody
    public void uploadUserFile(MultipartFile file){
        String fileName ="/upload/" + UploadFileUtils.getFileName(file,servletContext.getRealPath("/upload/"));

        userFileService.saveUserFile(fileName);
    }

    /**
     *  选择资料类型更新上传资料
     * @param id
     * @param fileType
     */
    @RequiredLogin
    @RequestMapping("/userFile_selectType")
    @ResponseBody
    public AjaxResult userFileSelectType(Long[] id, Long[] fileType){
        if (id != null && fileType != null && id.length == fileType.length){
            userFileService.userFileSelectType(id,fileType);
        }
        return new AjaxResult();
    }
}
