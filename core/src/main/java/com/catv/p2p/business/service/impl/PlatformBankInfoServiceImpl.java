package com.catv.p2p.business.service.impl;

import com.catv.p2p.base.query.PageResult;
import com.catv.p2p.business.domain.PlatformBankInfo;
import com.catv.p2p.business.mapper.PlatformBankInfoMapper;
import com.catv.p2p.business.query.PlatformBankInfoQueryObject;
import com.catv.p2p.business.service.IPlatformBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformBankInfoServiceImpl implements IPlatformBankInfoService {
	@Autowired
	private PlatformBankInfoMapper platformBankInfoMapper;

	public PageResult query(PlatformBankInfoQueryObject qo) {
		int count = this.platformBankInfoMapper.queryForCount(qo);
		if (count > 0) {
			List<PlatformBankInfo> list = this.platformBankInfoMapper.query(qo);
			return new PageResult(list, count, qo.getCurrentPage(),
					qo.getPageSize());
		}
		return PageResult.empty(qo.getPageSize());
	}

	public void saveOrUpdate(PlatformBankInfo bankInfo) {
		if (bankInfo.getId() != null) {
			this.platformBankInfoMapper.updateByPrimaryKey(bankInfo);
		} else {
			this.platformBankInfoMapper.insert(bankInfo);
		}
	}

	public List<PlatformBankInfo> listAll() {
		return this.platformBankInfoMapper.selectAll();
	}

    public PlatformBankInfo get(Long id) {
        return platformBankInfoMapper.selectByPrimaryKey(id);
    }
}
