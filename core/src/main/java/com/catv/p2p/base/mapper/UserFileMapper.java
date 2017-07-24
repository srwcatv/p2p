package com.catv.p2p.base.mapper;

import com.catv.p2p.base.domain.UserFile;
import com.catv.p2p.base.query.UserFileQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFileMapper {

	int insert(UserFile record);

	UserFile selectByPrimaryKey(Long id);

	List<UserFile> selectAll();

	int updateByPrimaryKey(UserFile record);

	/**
	 * 列出一个用户风控资料对象 hasType:如果为true,选择有类型的,如果为false,选择没有类型的
	 * 
	 * @param loginInfoId
	 * @param hasFileType
	 * @return
	 */
    List<UserFile> selectByApplierIdAndHasFileType(@Param("loginInfoId") Long loginInfoId, @Param("hasFileType") boolean hasFileType);

    List<UserFile> selectListByQo(UserFileQueryObject qo);

    Integer selectCountByQo(UserFileQueryObject qo);
}