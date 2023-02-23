package com.fc.pay.bill.download.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fc.pay.common.constants.PayConstants;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.fc.pay.bill.business.LocalDataBusiness;
import com.fc.pay.bill.config.UnionpayConfig;
import com.fc.pay.bill.download.OutsideDailyBillDownload;
import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.enums.BatchStatusEnum;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.bill.utils.ExceptionUtil;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SecureUtil;

/**
 * 银联日账单下载
 * @author zhanjq
 *
 */
@Component("unionpayDailyBillDownload")
public class UnionpayDailyBillDownload implements OutsideDailyBillDownload {
	
	private static final Logger log = LoggerFactory.getLogger(UnionpayDailyBillDownload.class);
	
	@Autowired
	private LocalDataBusiness dataBusiness;
	
	public void downloadBizBill(BillBizBatch batch) throws Exception {
		
		MerchantAppConfig config = dataBusiness.getMerchantAppConfigByMchId(batch.getChannelMerchantId());
		if(config==null ||
			StringUtils.isEmpty(config.getCertPath()) || 
			StringUtils.isEmpty(config.getCertPwd()) || 
			StringUtils.isEmpty(config.getCertValidate()) || 
			StringUtils.isEmpty(config.getCertEncPath())){
			throw new Exception("银联支付接口配置错误");
		}
		//log.info("config.getCertPath()>>>"+config.getCertPath());
		//log.info("config.getCertEncPath()>>>"+config.getCertEncPath());
		//log.info("config.getCertValidate()>>>"+config.getCertValidate());
		//SDKConfig.getConfig().loadPropertiesFromSrc(); //从classpath加载acp_sdk.properties文件
		//SDKConfig.getConfig().loadProperties(properties);//不同商户应用配置，通过数据库查询配置项信息 Properties对象，然后传递给银联运行时配置参数
		String merId = batch.getChannelMerchantId();
		//merId = "700000000000001";//注意:此处mock测试......		
		String settleDate = DateUtil.makeMMddDateFormat(batch.getBillDate());//eg: "0119";
		settleDate = "0119";//注意:此处mock测试......		
		//配置银联SDK运行属性
		try{
			UnionpayConfig.getConfig().initSDKConfig(config);
		}catch(Exception e){
			//batch.setHandleStatus(BatchStatusEnum.fail.name());
			//batch.setHandleRemark("银联支付初始化SDK配置失败>>>"+e.getMessage());
			throw new Exception("银联支付初始化SDK配置失败>>>"+e.getMessage());
		}
		
		Map<String, String> data = new HashMap<String, String>();		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		//版本号 全渠道默认值
		//data.put("version",  "5.0.0");  
		data.put("version",  UnionpayConfig.getConfig().getVersion());
		//字符集编码 可以使用UTF-8,GBK两种方式
		data.put("encoding", PayConstants.UTF8_ENCODING);  
		//签名方法 目前只支持01-RSA方式证书加密
		data.put("signMethod", "01");                        
		//交易类型 76-对账文件下载
		data.put("txnType", "76");                           
		//交易子类型 01-对账文件下载
		data.put("txnSubType", "01");
		//业务类型，固定
		data.put("bizType", "000000");                       
		
		//接入类型，商户接入填0，不需修改
		/***商户接入参数***/
		data.put("accessType", "0");                         
		//商户代码，请替换正式商户号测试，如使用的是自助化平台注册的777开头的商户号，该商户号没有权限测文件下载接口的，请使用测试参数里写的文件下载的商户号和日期测。如需777商户号的真实交易的对账文件，请使用自助化平台下载文件。
		data.put("merId", merId);                	         
		//清算日期，如果使用正式商户号测试则要修改成自己想要获取对账文件的日期， 测试环境如果使用700000000000001商户号则固定填写0119
		data.put("settleDate", settleDate);                  
		//订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		data.put("txnTime",DateUtil.makeDefaultTimeFormat(new Date())); 
		//文件类型，一般商户填写00即可
		data.put("fileType", "00");                          
		
		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/		
		//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		Map<String, String> reqData = AcpService.sign(data,PayConstants.UTF8_ENCODING);
		//获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.fileTransUrl
		String url = SDKConfig.getConfig().getFileTransUrl();
		Map<String, String> rspData =  AcpService.post(reqData,url,PayConstants.UTF8_ENCODING);

		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》		
		if(rspData==null || rspData.isEmpty()){
			log.error("未获取到返回报文或返回http状态码非200");
			//batch.setHandleStatus(BatchStatusEnum.fail.name());
			//batch.setHandleRemark("未获取到返回报文或返回http状态码非200");
			throw new Exception("未获取到返回报文或返回http状态码非200");
		}

		if(!AcpService.validate(rspData, PayConstants.UTF8_ENCODING)){
			log.error("验证签名失败");
			//batch.setHandleStatus(BatchStatusEnum.fail.name());
			//batch.setHandleRemark("验证签名失败");
			throw new Exception("验证签名失败");
		}

		String respCode = rspData.get("respCode");
		if(!"00".equals(respCode)){	
			//其他应答码为失败请排查原因
			//batch.setHandleStatus(BatchStatusEnum.fail.name());
			//batch.setHandleRemark(ExceptionUtil.descException(rspData));
			throw new Exception("文件传输类交易失败，respCode["+respCode+"]respMsg["+rspData.get("respMsg")+"]");
		}
	    
		//交易成功，解析返回报文中的fileContent并落地
		storeFileContent(rspData,batch,PayConstants.UTF8_ENCODING);
		
		batch.setHandleStatus(BatchStatusEnum.hasDownload.name());	
		batch.setHandleRemark("银联支付业务日账单已下载");

	}
	
	


	/**
	 * 解析交易返回的fileContent字符串并落地 （ 解base64，解DEFLATE压缩并落地）
	 * 
	 * @param resData 返回报文map
	 * @param batch 对账批次
	 * @param encoding 上送请求报文域encoding字段的值
	 * @return
	 */
	public static void storeFileContent(Map<String, String> resData, BillBizBatch batch, String encoding) throws Exception {
		// 解析返回文件
		String fileContent = resData.get(SDKConstants.param_fileContent);
		if(StringUtils.isEmpty(fileContent)){
			//batch.setHandleStatus(BatchStatusEnum.fail.name());
			//batch.setHandleRemark("银联支付账单内容为空");
			throw new Exception("银联支付账单内容为空");
		}
		FileOutputStream os = null;
		try {
			byte[] fileArray = SecureUtil.inflater(SecureUtil.base64Decode(fileContent.getBytes(encoding)));	
			//渠道日账单文件名
			String fileName = resData.get("fileName");
			if(StringUtils.isEmpty(fileName)){
				fileName = resData.get("merId")+"_"+ resData.get("settleDate")+".zip";
			}
			String mchId = batch.getChannelMerchantId();
			String appId = batch.getChannelAppId();//银联支付接口无appId，运行取值为null
			String dateStr = DateUtil.makeDefaultDateFormat(batch.getBillDate());
			//渠道日账单目录相对路径
			String outsideDailyBillDir = BillUtil.makeOutsideDailyBillDirPath(batch.getPayChannel(), mchId, appId, dateStr);
			String fileRelativePath = outsideDailyBillDir + fileName;
			//渠道日账单文件完整路径
			String fileDirectory = BillUtil.makeOutsideDailyBillDateDirExist(outsideDailyBillDir);			
			String filePath = fileDirectory + fileName;
			File file = new File(filePath);
			if (file.exists()) {
				log.debug("删除旧文件>>>"+file.getAbsolutePath());
				file.delete();
			}
			file.createNewFile();
			os = new FileOutputStream(file);
			os.write(fileArray, 0, fileArray.length);
			os.flush();
			//batch.setChannelBillStorePath(filePath);
			batch.setChannelBillStorePath(fileRelativePath);
		} catch (Exception e) {
			log.error(e.getMessage());
			//batch.setHandleStatus(BatchStatusEnum.fail.name());
			//batch.setHandleRemark(e.getMessage());
			throw e;
		}finally{
			IOUtils.closeQuietly(os);
		}
	}
	
	

}
