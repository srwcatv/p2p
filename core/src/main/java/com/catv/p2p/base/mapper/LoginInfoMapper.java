package com.catv.p2p.base.mapper;

import com.catv.p2p.base.domain.LoginInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginInfoMapper {
	int insert(LoginInfo record);

	LoginInfo selectByPrimaryKey(Long id);

	List<LoginInfo> selectAll();

	int updateByPrimaryKey(LoginInfo record);

	/**
	 * 根据用户名查询用户数量
	 * 
	 * @param username
	 * @return
	 */
	int getCountByUsername(String username);

	/**
	 * 登陆
	 * 
	 * @param username
	 * @param encode
	 * @return
	 */
	LoginInfo login(@Param("username") String username,
                    @Param("password") String encode, @Param("userType") int userType);

	/**
	 * 按照类型查询用户类型数量
	 * 
	 * @param userType
	 * @return
	 */
	int countByUserType(int userType);

    /**
     * 登陆
     * @param username
     * @param password
     * @param userType
     * @return
     */
    LoginInfo getByLogin(@Param("username") String username, @Param("password") String password, @Param("userType")int userType);

    /**
     * 查看是否有后台管理员
     * @param userManager
     * @return
     */
    int getByUserType(@Param("userType") int userManager);
}