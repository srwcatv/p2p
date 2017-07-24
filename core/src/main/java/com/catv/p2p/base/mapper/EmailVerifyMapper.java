package com.catv.p2p.base.mapper;

import com.catv.p2p.base.domain.EmailVerify;
import java.util.List;

public interface EmailVerifyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EmailVerify record);

    EmailVerify selectByPrimaryKey(Long id);

    List<EmailVerify> selectAll();

    int updateByPrimaryKey(EmailVerify record);

    EmailVerify selectByUuid(String uuid);
}