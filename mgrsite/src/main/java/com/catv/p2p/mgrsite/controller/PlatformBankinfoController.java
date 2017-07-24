package com.catv.p2p.mgrsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.catv.p2p.business.domain.PlatformBankInfo;
import com.catv.p2p.business.query.PlatformBankInfoQueryObject;
import com.catv.p2p.business.service.IPlatformBankInfoService;

/**
 * 平台账户管理
 * 
 * @author Administrator
 * 
 */
@Controller
public class PlatformBankinfoController {

	@Autowired
	private IPlatformBankInfoService platformBankInfoService;

	@RequestMapping("companyBank_list")
	public String platformBankinfoList(
			@ModelAttribute("qo") PlatformBankInfoQueryObject qo, Model model) {
		model.addAttribute("pageResult", this.platformBankInfoService.query(qo));
		return "platformbankinfo/list";
	}

	/**
	 * 修改或保存
	 */
	@RequestMapping("companyBank_update")
	public String update(PlatformBankInfo bankInfo) {
		this.platformBankInfoService.saveOrUpdate(bankInfo);
		return "redirect:/companyBank_list.do";
	}

}
