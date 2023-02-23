package com.fc.pay.geteway.controller.verify;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.enums.BillMakeStatusEnum;
import com.fc.pay.bill.enums.MistakeNotifyStatusEnum;
import com.fc.pay.bill.service.BillBizFileNotifyService;
import com.fc.pay.bill.service.BillBizMistakeService;
import com.fc.pay.common.core.enums.CodeEnum;
import com.fc.pay.trade.utils.Return;

/**
 * 商户应用账单操作接口实现
 * @author zhanjq
 *
 */
@Controller
@RequestMapping(value = "/verify")
public class NotifyVerifyController {
	
	private static final Logger log = LoggerFactory.getLogger(NotifyVerifyController.class);
	
	@Autowired
	private BillBizMistakeService mistakeService;
	
	@Autowired
	private BillBizFileNotifyService fileNotifyService;
	
	/**
	 * 验证交易异步通知合法性
	 * @param request
	 * @param response
	 * @param notifyId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/notify/pay")
	@ResponseBody
	public Return verifyPayNotify(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="notifyId",required=true) Long notifyId) throws Exception {		
		log.info("验证交易异步通知合法性开始>>>");	
		log.info("异步通知ID[notifyId]=>"+notifyId);
		////
		log.info("<<<验证交易异步通知合法性结束");
		Map result = new HashMap();
		result.put("pass", true);
		Return rtn = new Return(CodeEnum._10000.getName(), CodeEnum._10000.getValue(), result);
		return rtn;
	}

	/**
	 * 验证账单下载异步通知合法性
	 * @param request
	 * @param response
	 * @param notifyId
	 * @return
	 */
	@RequestMapping(value="/notify/bill/file")
	@ResponseBody
	public Return verifyBillFileNotify(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="notifyId",required=true) Long notifyId) {		
		log.info("验证账单下载异步通知合法性开始>>>");
		log.info("异步通知ID[notifyId]=>"+notifyId);		
		Boolean isValid = false;
		BillBizFileNotify fileNotify = fileNotifyService.get(notifyId);
		if(fileNotify!=null){
			if(BillMakeStatusEnum.success.name().equals(fileNotify.getFileStatus())){
				if(MistakeNotifyStatusEnum.send_success.name().equals(fileNotify.getNotifyStatus())){
					isValid = true;
					//超时作废机制???
				}
			}
		}		
		log.info("<<<验证账单下载异步通知合法性结束");
		Map result = new HashMap();
		result.put("pass", isValid);
		Return rtn = new Return(CodeEnum._10000.getName(), CodeEnum._10000.getValue(), result);
		return rtn;	
	}
	

	/**
	 * 验证对账差错处理异步通知合法性
	 * @param request
	 * @param response
	 * @param notifyId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/notify/bill/mistake")
	@ResponseBody
	public Return verifyBillMistakeNotify(HttpServletRequest request, HttpServletResponse response,  @RequestParam(value="notifyId",required=true) Long notifyId) throws Exception {		
		log.info("验证对账差错处理异步通知合法性开始>>>");	
		log.info("异步通知ID[notifyId]=>"+notifyId);		
		Boolean isValid = false;
		BillBizMistake mistake = mistakeService.get(notifyId);	
		if(mistake!=null){
			if(MistakeNotifyStatusEnum.send_success.name().equals(mistake.getNotifyStatus())){
				isValid = true;
				//超时作废机制???
			}
		}
		log.info("<<<验证对账差错处理异步通知合法性结束");
		Map result = new HashMap();
		result.put("pass", isValid);
		Return rtn = new Return(CodeEnum._10000.getName(), CodeEnum._10000.getValue(), result);
		return rtn;	
	}
	
}
