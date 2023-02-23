package com.fc.pay.controller.bill;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fc.pay.bill.business.BillBizCheckBatchBusiness;
import com.fc.pay.bill.business.BillBizCheckCoreBusiness;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.bill.enums.BillMakeStatusEnum;
import com.fc.pay.bill.service.BillBizBatchService;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.boss.enums.ErrorEnum;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.controller.base.BaseController;

/**
 * 业务日对账测试
 * @author zhanjq
 *
 */
@Controller
@RequestMapping("bill/test")
public class BillTestController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(BillTestController.class);
	
	@Autowired
	private BillBizCheckCoreBusiness billBizCheckCoreBusiness;
	
	@Autowired
	private BillBizBatchService billBizBatchService;
	
	/**
	 * 重启对账批次
	 * @param batchNo
	 * @return
	 */
	@RequestMapping("checkBiz")
	@ResponseBody
	public String checkBiz(@RequestParam(value="billPeriod",required=true) Integer billPeriod) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		Date billDate = DateUtil.addDay(new Date(), -billPeriod);
		String yyyyMMdd = DateUtil.makeDefaultDateFormat(billDate);
		try {
			billBizCheckCoreBusiness.checkBiz(billDate);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		respMap.put("billPeriod", billPeriod);
		respMap.put("billDate", yyyyMMdd);
		respMap.put("retCode", "000000");
		respMap.put("retMesg", "启动业务日对账");
		return JSON.toJSONString(respMap);
	}
	
	/**
	 * 查询渠道业务日对账单存储路径
	 */
	@RequestMapping(value="/batch/file/query",method=RequestMethod.GET)
	@ResponseBody
	public String billQuery(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="batchNo",required=true) String batchNo) {		
		log.info("渠道日账单查询开始>>>");		
		BillBizBatch batch = billBizBatchService.getByBatchNo(batchNo);
		String relativePath = batch.getChannelBillStorePath();
		log.info("账单db路径["+relativePath+"]");
		String billPath = BillUtil.readBillOutsidePath()+relativePath;
		log.info("账单绝对路径["+billPath+"]");
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("relativePath", relativePath);
		respMap.put("billPath", billPath);
		log.info("<<<渠道日账单查询结束");
		return JSON.toJSONString(respMap);
		
	}
	
	/**
	 * 下载业务日对账单
	 */
	@RequestMapping(value="/batch/file/download",method=RequestMethod.GET)
	public void billDownload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="runMode",required=true) String runMode,
			@RequestParam(value="batchNo",required=true) String batchNo) {		
		log.info("渠道日账单下载开始>>>");		
		BillBizBatch batch = billBizBatchService.getByBatchNo(batchNo);
		String relativePath = batch.getChannelBillStorePath();
		log.info("账单db路径["+relativePath+"]");
		String billPath = null;
		if("r".equals(runMode)){
			billPath = BillUtil.readBillOutsidePath()+relativePath;
		}else if("a".equals(runMode)){
			billPath = relativePath;
		}		
		log.info("账单绝对路径["+billPath+"]");
		/** 正常响应  */
		InputStream input = null;
		OutputStream output = null;
		try{
			input = new FileInputStream(new File(billPath));
			output = response.getOutputStream();	
			IOUtils.copyLarge(input, output);//内部byte[]buffer缓冲区大小为4096字节
		}catch(Exception e){
			log.error("下载渠道日对账单错误["+e.getMessage()+"]");
			/** 错误提示[错误码，错误消息]  */
		}finally{
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
		
		log.info("<<<渠道日账单下载结束");
	}
	
}
