package com.fc.pay.controller.dictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fc.pay.boss.entity.BossDictionary;
import com.fc.pay.boss.enums.ErrorEnum;
import com.fc.pay.boss.service.IBossDictionaryService;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.controller.base.BaseController;

/**
 * 系统首页
 * @author cjw
 *
 */
@Controller
@RequestMapping("dictionary")
public class DictionaryController extends BaseController{
	
	@Autowired
	private IBossDictionaryService bossDictionaryService;

	/**
	 * 商户管理界面
	 * @return
	 */
	@RequiresPermissions("pms:dictionary:list")
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		Map<String,Object> params = new HashMap<>();
		params.put("id", "0");
		List<BossDictionary> dictionarys = bossDictionaryService.selectList(params);
		model.addAttribute("dictionarys", dictionarys);
		return "dictionary/list";
	}
	
	/**
	 * 商户列表数据
	 * @param request
	 * @param current
	 * @param size
	 * @return
	 */
	@RequiresPermissions("pms:dictionary:list")
	@RequestMapping("listdata")
	@ResponseBody
	public String listData(HttpServletRequest request,Integer current,Integer size){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> params = this.getRequestParams(request);
			Page page = bossDictionaryService.selectPageList(params, current, size);
			result.put("data",page);
			result.put("total", page.getTotalItem());
			result.put("pages",page.getTotalPages());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result.put("errcode", ErrorEnum.SERVER_ERROR.getCode());
			result.put("errmsg", ErrorEnum.SERVER_ERROR.getMsg());
		}
		return JSON.toJSONString(result);
	}
	
	/**
	 * 跳至添加操作员页面
	 * @param request
	 * @param model
	 * @param parentMenu
	 * @return
	 */
	@RequiresPermissions("pms:dictionary:add")
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(HttpServletRequest request,Model model){
		Map<String,Object> params = new HashMap<>();
		params.put("id", "0");
		List<BossDictionary> dictionarys = bossDictionaryService.selectList(params);
		model.addAttribute("dictionarys", dictionarys);
		return "dictionary/add";
	}
	
	/**
	 * 新增操作员
	 * @param request
	 * @param model
	 * @param menu
	 * @return
	 */
	@RequiresPermissions("pms:dictionary:add")
	@RequestMapping(value="adddata",method=RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request,BossDictionary dictionary,Long[] roleId){
		Map<String,Object> info = new HashMap<>();
		int errcode = 0;
		String errmsg="";
		try {
			if(bossDictionaryService.isExisted(dictionary)){
				errcode = ErrorEnum.SAVE_ERROR.getCode();
				errmsg = "字典编码已存在";
			}else{
				boolean result = bossDictionaryService.insert(dictionary);
				if(result){
					errcode = ErrorEnum.SUCCESS.getCode();
					errmsg = ErrorEnum.SUCCESS.getMsg();
				}else{
					errcode = ErrorEnum.SAVE_ERROR.getCode();
					errmsg = ErrorEnum.SAVE_ERROR.getMsg();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			errcode = ErrorEnum.SERVER_ERROR.getCode();
			errmsg = ErrorEnum.SERVER_ERROR.getMsg();
		}
		
		info.put("errcode", errcode);
		info.put("errmsg", errmsg);
		return JSON.toJSONString(info);
	}
	

	@RequiresPermissions("pms:dictionary:edit")
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String edit(HttpServletRequest request,Model model,Long dictionaryId){
		Map<String,Object> params = new HashMap<>();
		params.put("id", "0");
		List<BossDictionary> dictionarys = bossDictionaryService.selectList(params);
		model.addAttribute("dictionarys", dictionarys);
		
		BossDictionary dictionary = bossDictionaryService.selectById(dictionaryId);
		model.addAttribute("dictionary", dictionary);
		return "dictionary/edit";
	}
	
	/**
	 * 修改账户
	 * @param request
	 * @param menu
	 * @return
	 */
	@RequiresPermissions("pms:dictionary:edit")
	@RequestMapping(value="editdata",method=RequestMethod.POST)
	@ResponseBody
	public String edit(HttpServletRequest request,Model model,BossDictionary dictionary){
		Map<String,Object> info = new HashMap<>();
		int errcode = 0;
		String errmsg="";
		try {
			if(bossDictionaryService.isExisted(dictionary)){
				errcode = ErrorEnum.UPDATE_ERROR.getCode();
				errmsg = "字典编码已存在";
			}else{
				boolean result = bossDictionaryService.updateSelectiveById(dictionary);
				if(result){
					errcode = ErrorEnum.SUCCESS.getCode();
					errmsg = ErrorEnum.SUCCESS.getMsg();
				}else{
					errcode = ErrorEnum.UPDATE_ERROR.getCode();
					errmsg = ErrorEnum.UPDATE_ERROR.getMsg();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			errcode = ErrorEnum.SERVER_ERROR.getCode();
			errmsg = ErrorEnum.SERVER_ERROR.getMsg();
		}
		// 更新
		info.put("errcode", errcode);
		info.put("errmsg", errmsg);
		return JSON.toJSONString(info);
	}
	
	/**
	 * 删除操作员
	 * @param request
	 * @param model
	 * @param menuId
	 * @return
	 */
	@RequiresPermissions("pms:dictionary:delete")
	@RequestMapping(value="delete",method=RequestMethod.GET)
	@ResponseBody
	public String delete(HttpServletRequest request,Long dictionaryId){
		Map<String,Object> info = new HashMap<>();
		int errcode = 0;
		String errmsg="";
		try {
			BossDictionary dictionary = new BossDictionary();
			dictionary.setParent(dictionaryId);
			if(bossDictionaryService.isExisted(dictionary)){
				errcode = ErrorEnum.DEL_ERROR.getCode();
				errmsg = "请先删除子字典";
			}else{
				boolean result  = bossDictionaryService.deleteById(dictionaryId);
				if(result){
					errcode = ErrorEnum.SUCCESS.getCode();
					errmsg = ErrorEnum.SUCCESS.getMsg();
				}else{
					errcode = ErrorEnum.DEL_ERROR.getCode();
					errmsg = ErrorEnum.DEL_ERROR.getMsg();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			errcode = ErrorEnum.SERVER_ERROR.getCode();
			errmsg = ErrorEnum.SERVER_ERROR.getMsg();
		}
		// 更新
		info.put("errcode", errcode);
		info.put("errmsg", errmsg);
		return JSON.toJSONString(info);
	}	
}
