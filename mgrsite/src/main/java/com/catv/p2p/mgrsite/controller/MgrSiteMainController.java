package com.catv.p2p.mgrsite.controller;

import com.catv.p2p.base.domain.SystemDictionary;
import com.catv.p2p.base.domain.SystemDictionaryItem;
import com.catv.p2p.base.query.IpLogQueryObject;
import com.catv.p2p.base.query.SystemDictionaryQueryObject;
import com.catv.p2p.base.service.IIpLogService;
import com.catv.p2p.base.service.ISystemDictionaryItemService;
import com.catv.p2p.base.service.ISystemDictionaryService;
import com.catv.p2p.base.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台管理页面
 */
@Controller
public class MgrSiteMainController {

    @Autowired
    IIpLogService ipLogService;

    @Autowired
    ISystemDictionaryService systemDictionaryService;

    @Autowired
    ISystemDictionaryItemService systemDictionaryItemService;

    /**
     * 日志列表
     *
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("ipLog")
    public String mgrIpLog(@ModelAttribute("qo") IpLogQueryObject qo, Model model) {
        model.addAttribute("result", ipLogService.queryPageResult(qo));
        return "ipLog/list";
    }

    /**
     * 数据字典分类列表
     *
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("/systemDictionary_list")
    public String systemDic(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model) {
        model.addAttribute("pageResult", systemDictionaryService.queryPageResult(qo));
        return "systemDic/systemDictionary_list";
    }

    /**
     * 数据字典分类保存和修改
     *
     * @param systemDictionary
     * @return
     */
    @RequestMapping("/systemDictionary_update")
    @ResponseBody
    public AjaxResult systemDicUpdateOrSave(SystemDictionary systemDictionary) {
        AjaxResult ajaxResult = new AjaxResult();
        if (systemDictionary.getId() != null) {
            int result = systemDictionaryService.update(systemDictionary);
            if (result > 0) {
                ajaxResult.setMsg("修改成功");
            } else {
                ajaxResult.setMsg("修改失败");
                ajaxResult.setSuccess(false);
            }
        } else {
            int result = systemDictionaryService.save(systemDictionary);
            if (result > 0) {
                ajaxResult.setMsg("保存成功");
            } else {
                ajaxResult.setMsg("保存失败");
                ajaxResult.setSuccess(false);
            }
        }
        return ajaxResult;
    }

    /**
     * 数据字典分类删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/systemDictionary_del")
    @ResponseBody
    public AjaxResult systemDicDelete(Long id) {
        AjaxResult ajaxResult = new AjaxResult();
        int result = systemDictionaryService.delete(id);
        if (result > 0) {
            ajaxResult.setMsg("删除成功");
        } else {
            ajaxResult.setMsg("删除失败");
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }

    /**
     * 数据字典明细列表
     *
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("/systemDictionaryItem_list")
    public String systemDicItem(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model) {
        model.addAttribute("systemDictionaryGroups",systemDictionaryService.getAll());
        model.addAttribute("pageResult", systemDictionaryItemService.queryPageResult(qo));
        return "systemDic/systemDictionaryItem_list";
    }

    /**
     * 修改或保存字典明细
     * @param systemDictionaryItem 保存或修改的字典明细
     * @return 返回修改或保存结果
     */
    @RequestMapping("/systemDictionaryItem_update")
    @ResponseBody
    public AjaxResult systemDicItemUpdateOrSave(SystemDictionaryItem systemDictionaryItem){
        if (systemDictionaryItem.getId() != null){
            int ret = systemDictionaryItemService.update(systemDictionaryItem);
            if (ret>0){
                return new AjaxResult("修改成功");
            } else {
                return new AjaxResult(false,"修改失败");
            }
        } else {
            int ret = systemDictionaryItemService.save(systemDictionaryItem);
            if (ret>0){
                return new AjaxResult("保存成功");
            } else {
                return new AjaxResult(false,"保存失败");
            }
        }
    }

    /**
     * 删除字典明细
     * @param id 对应的id
     * @return
     */
    @RequestMapping("/systemDictionaryItem_delete")
    @ResponseBody
    public AjaxResult systemDicItemDel(Long id){
        int ret = systemDictionaryItemService.delete(id);
        if (ret>0){
            return new AjaxResult("删除成功");
        } else {
            return new AjaxResult(false,"删除失败");
        }
    }
}
