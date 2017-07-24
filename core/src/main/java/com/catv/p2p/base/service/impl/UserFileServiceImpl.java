package com.catv.p2p.base.service.impl;

import com.catv.p2p.base.domain.UserFile;
import com.catv.p2p.base.domain.UserInfo;
import com.catv.p2p.base.mapper.UserFileMapper;
import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.base.query.UserFileQueryObject;
import com.catv.p2p.base.service.ISystemDictionaryItemService;
import com.catv.p2p.base.service.IUserFileService;
import com.catv.p2p.base.service.IUserInfoService;
import com.catv.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 风控资料
 */
@Service
public class UserFileServiceImpl implements IUserFileService {

    @Autowired
    private UserFileMapper userFileMapper;

    @Autowired
    private ISystemDictionaryItemService systemDictionaryItemService;

    @Autowired
    private IUserInfoService userInfoService;

    public List<UserFile> getByApplierIdAndHasFileType(Long loginInfoId, boolean hasFileType) {
        return userFileMapper.selectByApplierIdAndHasFileType(loginInfoId,hasFileType);
    }

    public void saveUserFile(String fileName) {
        UserFile userFile = new UserFile();
        userFile.setImage(fileName);
        userFile.setApplier(UserContext.getCurrent());
        userFile.setApplyTime(new Date());
        userFile.setState(UserFile.STATE_NORMAL);
        userFileMapper.insert(userFile);
    }

    public void userFileSelectType(Long[] id, Long[] fileType) {
        for (int i = 0; i < id.length; i++){
            UserFile userFile = userFileMapper.selectByPrimaryKey(id[i]);
            userFile.setFileType(systemDictionaryItemService.queryById(fileType[i]));
            userFileMapper.updateByPrimaryKey(userFile);
        }
    }

    public PageResult getPageResult(UserFileQueryObject qo) {
        return new PageResult(userFileMapper.selectListByQo(qo), userFileMapper.selectCountByQo(qo), qo.getCurrentPage(), qo.getPageSize());
    }

    public void audit(UserFile userFile) {
        UserFile current = userFileMapper.selectByPrimaryKey(userFile.getId());
        if (current != null && current.getState() == UserFile.STATE_NORMAL){
            current.setState(userFile.getState());
            current.setScore(userFile.getScore());
            current.setAuditor(UserContext.getCurrent());
            current.setAuditTime(new Date());
            current.setRemark(userFile.getRemark());
            UserInfo userInfo = userInfoService.getByPrimaryKey(current.getApplier().getId());
            if (userInfo != null && userFile.getState()==UserFile.STATE_AUDIT){
                userInfo.setScore(userInfo.getScore() + userFile.getScore());
                userInfoService.update(userInfo);
            }
            userFileMapper.updateByPrimaryKey(current);
        }
    }

    public List<UserFile> queryForList(UserFileQueryObject qo) {
        return userFileMapper.selectListByQo(qo);
    }
}
