package com.catv.p2p.base.service;

import com.catv.p2p.base.domain.UserFile;
import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.base.query.UserFileQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 风控资料相关
 */
public interface IUserFileService {
    /**
     *  获得当前用户上传的资料
     * @param loginInfoId 当前登陆用户id
     * @param hasFileType 是否有字典类型
     * @return
     */
    List<UserFile> getByApplierIdAndHasFileType(@Param("loginInfoId") Long loginInfoId, @Param("hasFileType") boolean hasFileType);

    /**
     * 保存上传的风控资料
     * @param fileName
     */
    void saveUserFile(String fileName);

    /**
     * 选择资料类型
     * @param id
     * @param fileType
     */
    void userFileSelectType(Long[] id, Long[] fileType);

    PageResult getPageResult(UserFileQueryObject qo);

    /**
     * 保存审核结果
     * @param userFile
     */
    void audit(UserFile userFile);

    List<UserFile> queryForList(UserFileQueryObject qo);
}
