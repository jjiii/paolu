package com.fc.pay.bill.job.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


































import redis.clients.jedis.Protocol;

import com.alibaba.druid.support.json.JSONUtils;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.fc.log.client.util.Properties;
//import com.alibaba.fastjson.JSON;
import com.fc.pay.bill.business.BillBizCheckBatchBusiness;
import com.fc.pay.bill.business.BillBizCheckCoreBusiness;
import com.fc.pay.bill.business.LocalDataBusiness;
import com.fc.pay.bill.config.AlipayConfig;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.entity.BillBizDoubt;
import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.bill.entity.BillBizItem;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.entity.BillBizSummary;
import com.fc.pay.bill.enums.BatchStatusEnum;
import com.fc.pay.bill.enums.BillMakeStatusEnum;
import com.fc.pay.bill.job.BillBizDailyDoubtClearJob;
import com.fc.pay.bill.service.BillBizBatchService;
import com.fc.pay.bill.service.BillBizCheckTranxService;
import com.fc.pay.bill.service.BillBizDoubtService;
import com.fc.pay.bill.service.BillBizFileNotifyService;
import com.fc.pay.bill.service.BillBizItemService;
import com.fc.pay.bill.service.BillBizMistakeService;
import com.fc.pay.bill.service.BillBizSummaryService;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.bill.utils.ExceptionUtil;
import com.fc.pay.bill.vo.BillBizCheckProgressData;
import com.fc.pay.common.constants.PayConstants;
import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.core.enums.TradeStatusEnum;
import com.fc.pay.common.core.utils.JsonUtil;
import com.fc.pay.common.pay.alipay.AliConfig;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.entity.PayRefundOrder;
import com.fc.pay.trade.entity.PayTradeRecord;
import com.fc.pay.trade.service.boss.IPayRefund;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.service.trade.IRefundOrder;
import com.fc.pay.trade.service.trade.ITradeRecord;

/**
 * 业务日对账测试
 * @author zhanjq
 *
 */
@Controller
@RequestMapping("bill/test")
public class BillTestController {
	
	private static final Logger log = LoggerFactory.getLogger(BillTestController.class);
	
	@Autowired
	private BillBizCheckCoreBusiness billBizCheckCoreBusiness;
	
	@Autowired
	private BillBizBatchService billBizBatchService;
	
	@Autowired
	private LocalDataBusiness dataBusiness;
	
	@Autowired
	private IPayRefund refundOrderService;
	
	@Autowired
	private IRefundOrder refundOrderDao;
	
	@Autowired
	private IPaymentOrder payOrderDao;
	
	@Autowired
	private ITradeRecord tradeService;
	
	/**
	 * 对账事务数据服务
	 */
	@Autowired
	private BillBizCheckTranxService tranxService;
	
	@Autowired
	private BillBizBatchService batchService;
	
	@Autowired
	private BillBizDoubtService doubtService;
	
	@Autowired
	private BillBizFileNotifyService fileService;
	
	@Autowired
	private BillBizItemService itemService;
	
	@Autowired
	private BillBizMistakeService mistakeService;
	
	@Autowired
	private BillBizSummaryService summaryService;
	
	@Autowired
	private IMerchantAppConfig merchantAppConfigService;
	
	@Autowired
	private BillBizFileNotifyService fnService;
	
	
	/**
	 * 获取日志配置信息log.properties
	 * @return
	 */
	@RequestMapping("/log/root/config")
	@ResponseBody
	public String showRootLogConfig() {
		String host = "localhost";
		int port = Protocol.DEFAULT_PORT;
		String key = null;
		String app = null;
		String env = null;
		Properties p = new Properties("log.properties");
		try {
			p.load();
			host = p.getProperty("log.host");
			port = Integer.parseInt(p.getProperty("log.port"));
			key = p.getProperty("log.key");
			app = p.getProperty("log.app");
			env = p.getProperty("log.env");
			return "host=>"+host+"<br/>"+"port=>"+port+"<br/>"+"key=>"+key+"<br/>"+"app=>"+app+"<br/>"+"env=>"+env;
		} catch (ConfigurationException e) {
			return ResponseUtil.fail(e).toString();
		}		
	}
	
	/**
	 * 获取日志配置信息log.properties
	 * @return
	 */
	@RequestMapping("/log/properties/config")
	@ResponseBody
	public String showPropertiesLogConfig() {
		String host = "localhost";
		int port = Protocol.DEFAULT_PORT;
		String key = null;
		String app = null;
		String env = null;
		Properties p = new Properties("properties/log.properties");
		try {
			p.load();
			host = p.getProperty("log.host");
			port = Integer.parseInt(p.getProperty("log.port"));
			key = p.getProperty("log.key");
			app = p.getProperty("log.app");
			env = p.getProperty("log.env");
			return "host=>"+host+"<br/>"+"port=>"+port+"<br/>"+"key=>"+key+"<br/>"+"app=>"+app+"<br/>"+"env=>"+env;
		} catch (ConfigurationException e) {
			return ResponseUtil.fail(e).toString();
		}
		
	}
	
	/**
	 * 获取日志配置信息log.properties
	 * @return
	 */
	@RequestMapping("/log/config")
	@ResponseBody
	public String showLogConfig() {
		String host = "localhost";
		int port = Protocol.DEFAULT_PORT;
		String key = null;
		String app = null;
		String env = null;

		Properties p = new Properties("log.properties");
		try {
			p.load();
		} catch (ConfigurationException e1) {
			p = new Properties("properties/log.properties");
			try {
				p.load();
			} catch (ConfigurationException e2) {
				return ResponseUtil.fail(e2).toString();
			}	
		}		
		host = p.getProperty("log.host");
		port = Integer.parseInt(p.getProperty("log.port"));
		key = p.getProperty("log.key");
		app = p.getProperty("log.app");
		env = p.getProperty("log.env");
		return "host=>"+host+"<br/>"+"port=>"+port+"<br/>"+"key=>"+key+"<br/>"+"app=>"+app+"<br/>"+"env=>"+env;
		
	}
	

	
	
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
			log.error(e.getMessage(), e);
		}
		respMap.put("billPeriod", billPeriod);
		respMap.put("billDate", yyyyMMdd);
		respMap.put("retCode", "000000");
		respMap.put("retMesg", "启动业务日对账");
		//return JSON.toJSONString(respMap);
		return "启动业务日对账";
	}
	
	/**
	 * 重启对账批次
	 * @param batchNo
	 * @return
	 */
	@RequestMapping("checkBiz/info")
	@ResponseBody
	public String checkBizInfo(@RequestParam(value="yyyyMMdd",required=true) String yyyyMMdd) {

		Date billDate = null;
		try {
			billDate = DateUtil.parseDefaultDateContent(yyyyMMdd);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		
		
		/*-------------------------------2.查询商户应用 ----------------------------*/
	    log.info("2.查询商户应用......");
	    List<MerchantApp> merchantAppList = dataBusiness.findMerchantAppList();//枫车商城，枫车快手
	    if(merchantAppList==null || merchantAppList.size()<=0){
	    	log.error("缺少商户应用配置，对账中止");
	    	return "";
	    }		
		/*------------------------------ 3.查询对账接口 -------------------------------*/
		log.info("3.查询对账接口......");
		List<MerchantAppConfig> macList = dataBusiness.findMerchantAppConfigList();
		if(CollectionUtils.isEmpty(macList)){
			log.info("缺少支付渠道接口配置，对账中止。");
			return "";
		}
		//对账接口去重处理（同个商户不同系统使用同个对账接口，如appid相同或者mchId相同）
		Map<String,MerchantAppConfig> macMap = new HashMap<String,MerchantAppConfig>();
		for(MerchantAppConfig mac : macList){
			String channel = mac.getChannel();
			String appId = mac.getChannelAppId();
			String mchId = mac.getChannelMerchantId();
			String macKey = null;
			if(PayChannelEnum.alipay.name().equals(channel) || PayChannelEnum.weixin.name().equals(channel)){
				macKey = channel+appId;
			}else if(PayChannelEnum.unionpay.name().equals(channel)){
				macKey = channel+mchId;
			}
			macMap.put(macKey, mac);
		}
		macList.clear();
		macList.addAll(macMap.values());
		if(CollectionUtils.isEmpty(macList)){
			log.info("支付渠道接口配置经过去重处理之后为空集，对账中止。");
			return "";
		}
		
	    /*-------------------------------4.准备过程数据 ----------------------------*/
		BillBizCheckProgressData data = tranxService.prepareProgressData(billDate, merchantAppList, macList);
		//BillBizSummary summary = data.getSummary();
		//List<BillBizBatch> batchList = data.getBatchList();
		//List<BillBizFileNotify> fileNotifyList = data.getFileNotifyList();
		
		return JsonUtil.toJson(data);
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
		//return JSON.toJSONString(respMap);
		return "relativePath>>>"+relativePath+"<br/>billPath>>>"+billPath;
		
	}
	
	/**
	 * 查询渠道业务日对账单存储路径
	 */
	@RequestMapping(value="/batch/get",method=RequestMethod.GET)
	@ResponseBody
	public String getBatchInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="batchNo",required=true) String batchNo) {	
		BillBizBatch batch = batchService.getByBatchNo(batchNo);
		return JsonUtil.toJson(batch);
	}
	
	/**
	 * 查询渠道业务日对账单存储路径
	 */
	@RequestMapping(value="/batch/deleteall",method=RequestMethod.GET)
	@ResponseBody
	public String deleteAllBillInfo() {	
		batchService.deleteAll();
		doubtService.deleteAll();
		fileService.deleteAll();
		itemService.deleteAll();
		mistakeService.deleteAll();
		summaryService.deleteAll();
		return JsonUtil.toJson("已删除数据");
	}
	
	@RequestMapping(value="/alipay/gateway",method=RequestMethod.GET)
	@ResponseBody
	public String getAlipayGateway(){	
		//return "网关地址=>"+AlipayConfig.getConfig().getGateway();
		return "网关地址=>"+AliConfig.url;
	}

	
	@RequestMapping(value="/downloadAlipayBill",method=RequestMethod.GET)
	@ResponseBody
	public String downloadAlipayBill(@RequestParam(value="channelAppId",required=true) String channelAppId,
			@RequestParam(value="billDate",required=true) String billDate){
		
		//MerchantAppConfig config = dataBusiness.getMerchantAppConfigByAppId(channelAppId);
		
		//BillBizBatch batch = batchService.getByBatchNo(batchNo);
		
		String billType = "trade";//业务对账使用trade，财务对账使用signcustomer
		//String billDate = DateUtil.makeHyphenDateFormat(batch.getBillDate());		
		/**------------------1.查询应用私有配置--------------------*/		
		//String channelAppId = batch.getChannelAppId();
		
		
		log.info("channelAppId=>"+channelAppId);
		MerchantAppConfig config = dataBusiness.getMerchantAppConfigByAppId(channelAppId);
		if(config==null || StringUtils.isEmpty(config.getPriKey()) || StringUtils.isEmpty(config.getPubKey())){
//			throw new Exception("支付宝支付接口配置错误");
			return JsonUtil.toJson("支付宝支付接口配置错误  appid=>"+config.getChannelAppId()+" prikey=>"+config.getPriKey()+" pubkey=>"+config.getPubKey());
		}		
		
		return "OK";
		//return "支付宝支付接口配置  appid=>"+config.getChannelAppId()+" prikey=>"+config.getPriKey()+" pubkey=>"+config.getPubKey()+"<br/>网关地址=>"+AlipayConfig.getConfig().getGateway();
		
		/**------------------1.查询账单下载地址--------------------*/		
		/******
		AlipayDataDataserviceBillDownloadurlQueryRequest request = null;
		AlipayDataDataserviceBillDownloadurlQueryResponse response = null;
		try {
			//AlipayClient alipayClient = new DefaultAlipayClient(sandboxGateway, appId,appPrivateKey, "json", "utf-8", alipayPublicKey);			
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.getConfig().getGateway(), channelAppId, 
					config.getPriKey(), PayConstants.JSON_STYLE, PayConstants.UTF8_ENCODING, config.getPubKey());
			request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
			request.setBizContent("{\"bill_type\":\""+billType+"\",\"bill_date\":\""+billDate+"\"}");			
			response = alipayClient.execute(request);	
		} catch (AlipayApiException e) {
			log.error("Alipay账单下载查询异常1："+e.getMessage());
//			batch.setHandleStatus(BatchStatusEnum.fail.name());			
//			batch.setHandleRemark(ExceptionUtil.descException(e));
			return JsonUtil.toJson(e);
		} catch(Exception e){
			log.error("Alipay账单下载查询异常2："+e.getMessage());
//			batch.setHandleStatus(BatchStatusEnum.fail.name());			
//			batch.setHandleRemark("download发生异常>>>"+e.getMessage());
			return JsonUtil.toJson(e);
		}
		if (!response.isSuccess()) {
			log.error("支付宝账单下载地址查询失败");
//			batch.setHandleStatus(BatchStatusEnum.fail.name());	
//			batch.setHandleRemark("支付宝账单下载地址查询失败");
			return JsonUtil.toJson(response);
		}
		log.info("支付宝账单下载地址（30秒内有效）>>>"+response.getBillDownloadUrl());
		*/
		/**------------------2.根据BillDownloadUrl下载对账单--------------------*/
		/*******
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		InputStream inputStream = null;
		String billFilePath = "----";
		try {
			HttpGet httpGet = new HttpGet(response.getBillDownloadUrl()); 
			httpResponse = httpclient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			log.info("支付宝账单下载字符集["+httpEntity.getContentEncoding()+"]");
			inputStream = httpEntity.getContent();
			String channel = "alipay";//batch.getPayChannel();
			String mchId = "123456";//batch.getChannelMerchantId();//支付宝接口无商户号mchId，运行取值为null
			String appId = channelAppId;//batch.getChannelAppId();
			String dateStr = billDate;//DateUtil.makeDefaultDateFormat(batch.getBillDate());
			//渠道日账单目录相对路径
			String outsideDailyBillDir = BillUtil.makeOutsideDailyBillDirPath(channel, mchId, appId, dateStr);
			//渠道日账单文件名
			String outsideBillName = appId+"_"+dateStr+".csv.zip";
			//渠道日账单文件完整路径
			billFilePath = BillUtil.makeOutsideDailyBillDateDirExist(outsideDailyBillDir)+outsideBillName;
			log.info("alipay账单保存路径："+billFilePath);
			File billFile = new File(billFilePath);
			if(billFile.exists()){
				log.debug("删除旧文件>>>"+billFile.getAbsolutePath());
				billFile.delete();
			}
			billFile.createNewFile();
			FileUtils.copyInputStreamToFile(inputStream, billFile);
			//batch.setChannelBillStorePath(billFilePath);
			//数据库记录只存储相对相对路径文件
			//batch.setChannelBillStorePath(outsideDailyBillDir+outsideBillName);
		} catch (Exception e) {
			log.error("Alipay账单下载操作异常："+e.getMessage());
			//batch.setHandleStatus(BatchStatusEnum.fail.name());			
			//batch.setHandleRemark("Alipay账单下载操作异常：["+e.getMessage()+"]");
			return JsonUtil.toJson(e);
		}finally{
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(httpResponse);
		}
		
		return JSONUtils.toJSONString(billFilePath);
		*/
	}
	
	@RequestMapping(value="/batch/check",method=RequestMethod.GET)
	@ResponseBody
	public String checkBatch(@RequestParam(value="batchNo",required=true) String batchNo){
		
		BillBizBatch batch = batchService.getByBatchNo(batchNo);
		
		String billType = "trade";//业务对账使用trade，财务对账使用signcustomer
		String billDate = DateUtil.makeHyphenDateFormat(batch.getBillDate());
		
		/**------------------1.查询应用私有配置--------------------*/		
		String channelAppId = batch.getChannelAppId();
		
		
		log.info("channelAppId=>"+channelAppId);
		MerchantAppConfig config = dataBusiness.getMerchantAppConfigByAppId(channelAppId);
		if(config==null || StringUtils.isEmpty(config.getPriKey()) || StringUtils.isEmpty(config.getPubKey())){
//			throw new Exception("支付宝支付接口配置错误");
			return JsonUtil.toJson("支付宝支付接口配置错误"+config);
		}		
		
		/**------------------1.查询账单下载地址--------------------*/		
		AlipayDataDataserviceBillDownloadurlQueryRequest request = null;
		AlipayDataDataserviceBillDownloadurlQueryResponse response = null;
		try {
			//AlipayClient alipayClient = new DefaultAlipayClient(sandboxGateway, appId,appPrivateKey, "json", "utf-8", alipayPublicKey);
			AlipayClient alipayClient = new DefaultAlipayClient(AliConfig.url, batch.getChannelAppId(), 
					config.getPriKey(), PayConstants.JSON_STYLE, PayConstants.UTF8_ENCODING, config.getPubKey());
			request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
			request.setBizContent("{\"bill_type\":\""+billType+"\",\"bill_date\":\""+billDate+"\"}");			
			response = alipayClient.execute(request);		
		} catch (AlipayApiException e) {
			log.error("Alipay账单下载查询异常1："+e.getMessage());
			batch.setHandleStatus(BatchStatusEnum.fail.name());			
			batch.setHandleRemark(ExceptionUtil.descException(e));
			return JsonUtil.toJson(e);
		} catch(Exception e){
			log.error("Alipay账单下载查询异常2："+e.getMessage());
			batch.setHandleStatus(BatchStatusEnum.fail.name());			
			batch.setHandleRemark("download发生异常>>>"+e.getMessage());
			return JsonUtil.toJson(e);
		}
		if (!response.isSuccess()) {
			log.error("支付宝账单下载地址查询失败");
			batch.setHandleStatus(BatchStatusEnum.fail.name());	
			batch.setHandleRemark("支付宝账单下载地址查询失败");
			return JsonUtil.toJson(response);
		}
		log.info("支付宝账单下载地址（30秒内有效）>>>"+response.getBillDownloadUrl());
		
		/**------------------2.根据BillDownloadUrl下载对账单--------------------*/
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		InputStream inputStream = null;
		try {
			HttpGet httpGet = new HttpGet(response.getBillDownloadUrl()); 
			httpResponse = httpclient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			log.info("支付宝账单下载字符集["+httpEntity.getContentEncoding()+"]");
			inputStream = httpEntity.getContent();
			String channel = batch.getPayChannel();
			String mchId = batch.getChannelMerchantId();//支付宝接口无商户号mchId，运行取值为null
			String appId = batch.getChannelAppId();
			String dateStr = DateUtil.makeDefaultDateFormat(batch.getBillDate());
			//渠道日账单目录相对路径
			String outsideDailyBillDir = BillUtil.makeOutsideDailyBillDirPath(channel, mchId, appId, dateStr);
			//渠道日账单文件名
			String outsideBillName = appId+"_"+dateStr+".csv.zip";
			//渠道日账单文件完整路径
			String billFilePath = BillUtil.makeOutsideDailyBillDateDirExist(outsideDailyBillDir)+outsideBillName;
			log.info("alipay账单保存路径："+billFilePath);
			File billFile = new File(billFilePath);
			if(billFile.exists()){
				log.debug("删除旧文件>>>"+billFile.getAbsolutePath());
				billFile.delete();
			}
			billFile.createNewFile();
			FileUtils.copyInputStreamToFile(inputStream, billFile);
			//batch.setChannelBillStorePath(billFilePath);
			//数据库记录只存储相对相对路径文件
			batch.setChannelBillStorePath(outsideDailyBillDir+outsideBillName);
		} catch (Exception e) {
			log.error("Alipay账单下载操作异常："+e.getMessage());
			batch.setHandleStatus(BatchStatusEnum.fail.name());			
			batch.setHandleRemark("Alipay账单下载操作异常：["+e.getMessage()+"]");
			return JsonUtil.toJson(e);
		}finally{
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(httpResponse);
		}
		
		return JsonUtil.toJson(batch);
	}
	
	@RequestMapping(value="/mistake/getById",method=RequestMethod.GET)
	@ResponseBody
	public String getMistakeById(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="mistakeId",required=true) String mistakeId) {	
		BillBizMistake mistake = mistakeService.get(Long.valueOf(mistakeId));
		return JsonUtil.toJson(mistake);
	}
	
	@RequestMapping(value="/mistake/list",method=RequestMethod.GET)
	@ResponseBody
	public String getMistakeList(HttpServletRequest request, HttpServletResponse response) {	
		List<BillBizMistake> mistakeList = mistakeService.list(null);
		return JsonUtil.toJson(mistakeList);
	}
	
	@RequestMapping(value="/doubt/getById",method=RequestMethod.GET)
	@ResponseBody
	public String getDoubtById(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="doubtId",required=true) String doubtId) {	
		BillBizDoubt doubt = doubtService.get(Long.valueOf(doubtId));
		return JsonUtil.toJson(doubt);
	}
	
	@RequestMapping(value="/doubt/list",method=RequestMethod.GET)
	@ResponseBody
	public String getDoubtList(HttpServletRequest request, HttpServletResponse response) {	
		List<BillBizDoubt> doubtList = doubtService.list(null);
		return JsonUtil.toJson(doubtList);
	}
	
	@RequestMapping(value="/bizItem/getById",method=RequestMethod.GET)
	@ResponseBody
	public String getBizItemById(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="itemId",required=true) String itemId) {	
		BillBizItem bizItem = itemService.get(Long.valueOf(itemId));
		return JsonUtil.toJson(bizItem);
	}
	
	@RequestMapping(value="/bizItem/list",method=RequestMethod.GET)
	@ResponseBody
	public String getBizItemList(HttpServletRequest request, HttpServletResponse response) {	
		List<BillBizItem> itemList = itemService.list(null);
		return JsonUtil.toJson(itemList);
	}
	
	@RequestMapping(value="/refund/all/list",method=RequestMethod.GET)
	@ResponseBody
	public String findAllRefundDataByBatch(@RequestParam(value="batchNo",required=true) String batchNo){		
		BillBizBatch batch = batchService.getByBatchNo(batchNo);
		//List<PayRefundOrder> list = new  ArrayList<PayRefundOrder>();
		List<PayRefundOrder> list = dataBusiness.findSuccessRefundDataByBatch(batch);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("channel", batch.getPayChannel());
		map.put("channelMerchantId", batch.getChannelMerchantId());
		map.put("channelAppId", batch.getChannelAppId());
		String yyyyMMdd = DateUtil.makeDefaultDateFormat(batch.getBillDate());
		map.put("startTime", yyyyMMdd+"000000");
		map.put("endTime",   yyyyMMdd+"235959");
		//map.put("refundStatus", TradeStatusEnum.success.name());
		return "condition-map:<br/>"+JsonUtil.toJson(map)+"<br/> result-list:<br/>"+JsonUtil.toJson(list);
	}
	
	@RequestMapping(value="/refund/success/list",method=RequestMethod.GET)
	@ResponseBody
	public String findSuccessRefundDataByBatch(@RequestParam(value="batchNo",required=true) String batchNo){		
		BillBizBatch batch = batchService.getByBatchNo(batchNo);
		//List<PayRefundOrder> list = new  ArrayList<PayRefundOrder>();
		List<PayRefundOrder> list = dataBusiness.findSuccessRefundDataByBatch(batch);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("channel", batch.getPayChannel());
		map.put("channelMerchantId", batch.getChannelMerchantId());
		map.put("channelAppId", batch.getChannelAppId());
		String yyyyMMdd = DateUtil.makeDefaultDateFormat(batch.getBillDate());
		map.put("startTime", yyyyMMdd+"000000");
		map.put("endTime",   yyyyMMdd+"235959");
		map.put("refundStatus", TradeStatusEnum.success.name());
		return "condition-map:<br/>"+JsonUtil.toJson(map)+"<br/> result-list:<br/>"+JsonUtil.toJson(list);
	}
	
	@RequestMapping(value="/refund/success/list/fix",method=RequestMethod.GET)
	@ResponseBody
	public String findFixSuccessRefundDataByBatch(){		
		//List<PayRefundOrder> list = new  ArrayList<PayRefundOrder>();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("channel", "alipay");
		map.put("channelMerchantId", null);
		map.put("channelAppId", "2017010904939710");
//		map.put("startTime", "20170331000000");
//		map.put("endTime",   "20170331235959");
		map.put("startTime", null);
		map.put("endTime",   null);
		map.put("refundStatus", "success");
		List<PayRefundOrder> list = refundOrderDao.listForBill(map);		
		return "condition-map:<br/>"+JsonUtil.toJson(map)+"<br/> result-list:<br/>"+JsonUtil.toJson(list);
	}
	
	@RequestMapping(value="/pay/success/list/fix/2",method=RequestMethod.GET)
	@ResponseBody
	public String findFixSuccessPayDataByBatch2(){		
		//List<PayRefundOrder> list = new  ArrayList<PayRefundOrder>();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("channel", "alipay");
		map.put("channelMerchantId", null);
		map.put("channelAppId", "2017010904939710");
//		map.put("startTime", "20170331000000");
//		map.put("endTime",   "20170331235959");
//		map.put("startTime", null);
//		map.put("endTime",   null);
		map.put("status", "success");
		List<PayPaymentOrder> list = payOrderDao.listForBill(map);		
		return "condition-map:<br/>"+JsonUtil.toJson(map)+"<br/> result-list:<br/>"+JsonUtil.toJson(list);
	}
	
	@RequestMapping(value="/pay/success/list/fix",method=RequestMethod.GET)
	@ResponseBody
	public String findFixSuccessPayDataByBatch(){		
		//List<PayRefundOrder> list = new  ArrayList<PayRefundOrder>();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("channel", "alipay");
		map.put("channelMerchantId", null);
		map.put("channelAppId", "2017010904939710");
		map.put("startTime", "20170331000000");
		map.put("endTime",   "20170331235959");
//		map.put("startTime", null);
//		map.put("endTime",   null);
		map.put("status", "success");
		List<PayPaymentOrder> list = payOrderDao.listForBill(map);		
		return "condition-map:<br/>"+JsonUtil.toJson(map)+"<br/> result-list:<br/>"+JsonUtil.toJson(list);
	}
	
	@RequestMapping(value="/pay/all/list/fix",method=RequestMethod.GET)
	@ResponseBody
	public String findFixAllPayDataByBatch(){		
		//List<PayRefundOrder> list = new  ArrayList<PayRefundOrder>();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("channel", "alipay");
		map.put("channelMerchantId", null);
		map.put("channelAppId", "2017010904939710");
		map.put("startTime", "20170331000000");
		map.put("endTime",   "20170331235959");
//		map.put("startTime", null);
//		map.put("endTime",   null);
//		map.put("status", "success");
		List<PayPaymentOrder> list = payOrderDao.listForBill(map);		
		return "condition-map:<br/>"+JsonUtil.toJson(map)+"<br/> result-list:<br/>"+JsonUtil.toJson(list);
	}
	
//	@RequestMapping(value="/refund/success/select",method=RequestMethod.GET)
//	@ResponseBody
//	public String findSuccessRefundDataByBatch(){		
//		Map<String, Object> map = new HashMap<String,Object>();
//		map.put("channel", "alipay");
//		map.put("channelMerchantId", "123456");
//		map.put("channelAppId", "000000");
//		String yyyyMMdd = DateUtil.makeDefaultDateFormat(new Date());
//		map.put("startTime", yyyyMMdd+"000000");
//		map.put("endTime",   yyyyMMdd+"235959");
//		map.put("refundStatus", TradeStatusEnum.success.name());
//		return JsonUtil.toJson(refundOrderDao.listForBill(map));
//		//return "condition-map:<br/>"+JsonUtil.toJson(map)+"<br/> result-list:<br/>"+JsonUtil.toJson(list);
//	}
	
	@RequestMapping(value="/refund/get",method=RequestMethod.GET)
	@ResponseBody
	public String getRefundOrder(@RequestParam(value="refundNo",required=true) String refundNo){
		PayRefundOrder refundOrder = refundOrderDao.getByRefundNo(refundNo);
		return JsonUtil.toJson(refundOrder);
	}
	
	@RequestMapping(value="/payorder/get",method=RequestMethod.GET)
	@ResponseBody
	public String getPayOrder(@RequestParam(value="orderNo",required=true) String orderNo){
		PayPaymentOrder  payOrder = payOrderDao.getByOrderNo(orderNo);
		return JsonUtil.toJson(payOrder);
	}
	
	@RequestMapping(value="/tradeRecord/get",method=RequestMethod.GET)
	@ResponseBody
	public String getTradeOrder(@RequestParam(value="orderNo",required=true) String orderNo){
		return JsonUtil.toJson(tradeService.getByOrderNo(orderNo));
	}
	
	
	
	@RequestMapping(value="/refund/list",method=RequestMethod.GET)
	@ResponseBody
	public String listRefundOrder(){
		//PayRefundOrder refundOrder = refundOrderDao.page(null);
		//return JsonUtil.toJson(refundOrder);
		return null;
	}
	
	@RequestMapping(value="/merchantAppConfig/set",method=RequestMethod.GET)
	@ResponseBody
	public String setMerchantAppConfig(@RequestParam(value="merchantAppConfigId",required=true) Long merchantAppConfigId,
			@RequestParam(value="status",required=true) Integer status){
		MerchantAppConfig merchantAppConfig = merchantAppConfigService.get(merchantAppConfigId);
		merchantAppConfig.setStatus(status);
		merchantAppConfigService.modify(merchantAppConfig);
		return "OK";
	}
	
	@RequestMapping(value="/merchantAppConfig/list",method=RequestMethod.GET)
	@ResponseBody
	public String listMerchantAppConfig(@RequestParam(value="merchantAppCode",required=true) String merchantAppCode){
		return JsonUtil.toJson(merchantAppConfigService.list(merchantAppCode));
	}
	
	@RequestMapping(value="/nofity/list",method=RequestMethod.GET)
	@ResponseBody
	public String listFileNotify(@RequestParam(value="yyyyMMdd",required=true) String yyyyMMdd) throws ParseException{
		return JsonUtil.toJson(fnService.listByBillDate(DateUtil.parseDefaultDateContent(yyyyMMdd)));
	}
	
	/**
	 * 下载业务日对账单
	 * @throws IOException 
	 */
	@RequestMapping(value="/merchantApp/bill/download",method=RequestMethod.GET)
	public void downloadMerchantAppBill(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="fnId",required=true) Long fnId) throws Exception {		
		log.info("渠道日账单下载开始>>>");		
		BillBizFileNotify fn = fnService.get(fnId);
		String billPath = BillUtil.readBillInsidePath()+fn.getFilePath();
		InputStream input = null;
		OutputStream output = null;
		try{
			input = new FileInputStream(new File(billPath));
			output = response.getOutputStream();
			IOUtils.copyLarge(input, output);//内部byte[]buffer缓冲区大小为4096字节
		}catch(Exception e){
			throw e;
		}finally{
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}
	
	/**
	 * 下载业务日对账单
	 * @throws IOException 
	 */
	@RequestMapping(value="/batch/file/download",method=RequestMethod.GET)
	public void billDownload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="runMode",required=true) String runMode,
			@RequestParam(value="batchNo",required=true) String batchNo) throws Exception {		
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
		
		/***/
		///** 正常响应  /
		InputStream input = null;
		OutputStream output = null;
		try{
			input = new FileInputStream(new File(billPath));
			output = response.getOutputStream();	
			IOUtils.copyLarge(input, output);//内部byte[]buffer缓冲区大小为4096字节
		}catch(Exception e){
			log.error("下载渠道日对账单错误["+e.getMessage()+"]");
			throw e;
		}finally{
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
		
		log.info("<<<渠道日账单下载结束");
		/***/
	}
	
}
