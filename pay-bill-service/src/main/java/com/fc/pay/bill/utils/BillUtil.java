package com.fc.pay.bill.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fc.pay.bill.enums.FileStoreModeEnum;
import com.fc.pay.common.core.utils.PathUtil;

/**
 * 账单配置工具类
 * 
 * @author zhanjq
 *
 */
public class BillUtil {
	
	private static final Logger log = LoggerFactory.getLogger(BillUtil.class);

	private static Properties properties = new Properties();

	private BillUtil() {

	}

	static {
		try {
			properties.load(BillUtil.class.getClassLoader().getResourceAsStream("properties/bill_config.properties"));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 读取账单配置项
	 * @param key
	 * @return
	 */
	public static String readBillConfig(String key) {
		return (String) properties.get(key);
	}
	
	/**
	 * 读取文件模式配置项
	 * @param key
	 * @return
	 */
	//public static String readFileStoreMode() {
	//	return readBillConfig("FileStoreMode");
	//}
	/*
	public static String readBillBasicPath(){
		log.info("ContextClassLoader.Resource.Path>>>"+Thread.currentThread().getContextClassLoader().getResource("").getPath());
		//String fileStoreMode = readFileStoreMode();
		//log.info("fileStoreMode>>>"+fileStoreMode);
		//if(FileStoreModeEnum.batch.name().equals(fileStoreMode)){
		//	return Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("conf/", "bill/");
		//}else if(FileStoreModeEnum.web.name().equals(fileStoreMode)){
		return Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("classes/", "bill/");//针对web路径修改
		//}		
		//return "";
	}
	*/
	
	/**
	 * 读取外部账单路径基础地址
	 * @return
	 */
	public static String readBillOutsidePath(){
		return PathUtil.readBillBasicPath()+readBillConfig("BillOutsidePath");		
	}
	
	/**
	 * 读取内部账单路径基础地址
	 * @return
	 */
	public static String readBillInsidePath(){
		return PathUtil.readBillBasicPath()+readBillConfig("BillInsidePath");
	}
	
	/**
	 * 读取对账周期（天数）
	 * @return
	 */
	public static String readBillPeriod(){
		return readBillConfig("BillPeriod");
	}
	
	/**
	 * 读取存疑清理周期（天数）
	 * @return
	 */
	public static String readDoubtClearPeriod(){
		return readBillConfig("DoubtClearPeriod");
	}
	
	/**
	 * 读取账单下载重试次数
	 * 
	 * @return
	 */
	public static int getBillDownloadRetryTimes(){
		return Integer.valueOf(readBillConfig("BillDownloadTryTimes"));
	}
	
	
	/**
	 * 读取证书存放目录
	 * @return
	 */
	/*
	public static String readCertBasicPath(){
		log.info("ContextClassLoader.Resource.Path>>>"+Thread.currentThread().getContextClassLoader().getResource("").getPath());
		//String fileStoreMode = readFileStoreMode();
		//log.info("fileStoreMode>>>"+fileStoreMode);
		//if(FileStoreModeEnum.batch.name().equals(fileStoreMode)){
		//	return Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("conf/", "cert/");
		//}else if(FileStoreModeEnum.web.name().equals(fileStoreMode)){
		return Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("classes/", "cert/");//针对web路径修改
		//}		
		//return "";
	}
	*/
	
	/**
	 * 读取支付宝支付交易标记
	 * @return
	 */
	public static String readAlipayTranTagPay(){
		return readBillConfig("AlipayTranTagPay");
	}
	
	/**
	 * 读取支付宝退款交易标记
	 * @return
	 */
	public static String readAlipayTranTagRefund(){
		return readBillConfig("AlipayTranTagRefund");
	}
	
	/**
	 * 读取微信支付交易标记
	 * @return
	 */
	public static String readWeixinTranTagPay(){
		return readBillConfig("WeixinTranTagPay");
	}
	
	/**
	 * 读取微信退款交易标记
	 * @return
	 */
	public static String readWeixinTranTagRefund(){
		return readBillConfig("WeixinTranTagRefund");
	}
	
	/**
	 * 读取银联支付交易标记
	 * @return
	 */
	public static String readUnionpayTranTagPay(){
		return readBillConfig("UnionpayTranTagPay");
	}
	
	/**
	 * 读取银联退款交易标记
	 * @return
	 */
	public static String readUnionpayTranTagRefund(){
		return readBillConfig("UnionpayTranTagRefund");
	}
	
	/**
	 * 确保外部渠道日账单目录存在
	 * @param channel
	 * @param mchId
	 * @param appId
	 * @param yyyyMMdd
	 * @return
	 */
	/**
	public static String makeOutsideDailyBillDateDirExist(String channel, String mchId, String appId, String yyyyMMdd){
		String dateBillPath = makeOutsideDailyBillDirPath(channel, mchId, appId, yyyyMMdd);
		File dir = new File(dateBillPath);
		if(!dir.exists()){
			if(!dir.mkdirs()){
				log.error("创建日账单目录["+dateBillPath+"]失败");
				return null;
			}
		}
		return dateBillPath;
	}
	*/
	public static String makeOutsideDailyBillDateDirExist(String outsideDailyBillDir){
		String fullOutsidePath = BillUtil.readBillOutsidePath()+outsideDailyBillDir;
		File dir = new File(fullOutsidePath);
		if(!dir.exists()){
			if(!dir.mkdirs()){
				log.error("创建日账单目录["+fullOutsidePath+"]失败");
				return null;
			}
		}
		return fullOutsidePath;
	}
	
	/**
	 * 生成渠道应用日账单存储相对路径
	 * @param channel
	 * @param yyyyMMdd
	 * @return
	 */
	public static String makeOutsideDailyBillDirPath(String channel, String mchId, String appId, String yyyyMMdd){
		StringBuffer buffer = new StringBuffer();
		//buffer.append(BillUtil.readBillOutsidePath());
		buffer.append(channel+"/");
		if(!StringUtils.isEmpty(mchId)){
			buffer.append(mchId+"/");
		}
		if(!StringUtils.isEmpty(appId)){
			buffer.append(appId+"/");
		}			
		buffer.append(yyyyMMdd+"/");
		System.out.println("outpath>>>"+buffer.toString());
		return buffer.toString();
	}
	
	/**
	 * 确保内部应用日账单目录存在
	 * @param merchantCode	
	 * @param merchantAppCode
	 * @param yyyyMMdd
	 * @return
	 */
//	public static String makeInsideDailyBillDateDirExist(String merchantCode, String merchantAppCode, String yyyyMMdd){
//		String dateBillPath = BillUtil.makeInsideDailyBillDirPath(merchantCode, merchantAppCode, yyyyMMdd);
//		File dir = new File(dateBillPath);
//		if(!dir.exists()){
//			if(!dir.mkdirs()){
//				log.error("创建日账单目录["+dateBillPath+"]失败");
//				return null;
//			}
//		}
//		return dateBillPath;
//	}
	public static String makeInsideDailyBillDateDirExist(String insideDailyBillDir){
		String fullInsidePath = BillUtil.readBillInsidePath()+insideDailyBillDir;
		File dir = new File(fullInsidePath);
		if(!dir.exists()){
			if(!dir.mkdirs()){
				log.error("创建日账单目录["+fullInsidePath+"]失败");
				return null;
			}
		}
		return fullInsidePath;
	}
	
	/**
	 * 生成商户应用日账单存储目录
	 * @param merchantCode
	 * @param merchantAppCode
	 * @param yyyyMMdd
	 * @return
	 */
	public static String makeInsideDailyBillDirPath(String merchantCode, String merchantAppCode, String yyyyMMdd){
		StringBuffer buffer = new StringBuffer();
		//buffer.append(BillUtil.readBillInsidePath());
		buffer.append(merchantCode+"/");
		buffer.append(merchantAppCode+"/");
		buffer.append(yyyyMMdd+"/");
		System.out.println("inpath>>>"+buffer.toString());
		return buffer.toString();
	}
	
	/**
	 * 生成商户应用日账单文件名称
	 * @param merchantCode
	 * @param merchantAppCode
	 * @param yyyyMMdd
	 * @return
	 */
	public static String makeInsideDailyBillFileName(String merchantCode, String merchantAppCode, String yyyyMMdd){
		StringBuffer buffer = new StringBuffer();
		buffer.append(merchantCode);
		buffer.append("_");
		buffer.append(merchantAppCode);
		buffer.append("_");
		buffer.append(yyyyMMdd);
		buffer.append(".zip");
		return buffer.toString();
	}
	
	/**
	 * 生成商户应用日账单完整文件路径
	 * @return
	 */
	public static String makeInsideDailyBillDownloadFileFullPath(String merchantCode, String merchantAppCode, String yyyyMMdd){
		return makeInsideDailyBillDirPath(merchantCode, merchantAppCode, yyyyMMdd)+makeInsideDailyBillFileName(merchantCode, merchantAppCode, yyyyMMdd);
	}
	
	/**
	 * 提取渠道业务明细账单文件
	 * 
	 * @param dir
	 * @param fileFilter
	 * @return
	 */
	public static File extractChannelBizItemBillFile(File dir, IOFileFilter fileFilter){
		File[] fileArray = FileUtils.convertFileCollectionToFileArray(FileUtils.listFiles(dir, fileFilter, null));
		if(fileArray==null || fileArray.length != 1){
			return null;
		}
		return fileArray[0];
	}
	
	public static void main(String[] args){
		log.info("BillBasicPath=>"+BillUtil.readBillConfig("BillBasicPath"));
		log.info("BillOutsidePath=>"+BillUtil.readBillConfig("BillOutsidePath"));
		log.info("BillInsidePath=>"+BillUtil.readBillConfig("BillInsidePath"));
		/**
		占位符未替换!!!!!!
		18:41:24.563 [main] INFO  c.f.p.b.b.BillBizDownloadBusiness - BillPath=>d:/bill/daily/
		18:41:24.566 [main] INFO  c.f.p.b.b.BillBizDownloadBusiness - BillPathOutside=>${BillPath}outside/
		18:41:24.566 [main] INFO  c.f.p.b.b.BillBizDownloadBusiness - BillPathInside=>${BillPath}inside/
		 */
		log.info("-------------------------- 外部渠道日账单 ---------------------------");
//		log.info("银联渠道=>"+BillUtil.makeOutsideDailyBillDateDirExist("unionpay","mch001","app001","20161115"));		
//		log.info("微信渠道=>"+BillUtil.makeOutsideDailyBillDateDirExist("weixin","mch002","app002","20161116"));
//		log.info("支付宝渠道=>"+BillUtil.makeOutsideDailyBillDateDirExist("alipay","mch003","app003","20161117"));
		log.info("-------------------------- 外部渠道日账单 ---------------------------");
//		log.info("商城应用=>"+BillUtil.makeInsideDailyBillDateDirExist("fc","shagncheng","20161115"));		
//		log.info("快手应用=>"+BillUtil.makeInsideDailyBillDateDirExist("fc","kuaishou","20161116"));
		
		System.out.println(ClassLoader.getSystemResource("").getPath());
		System.out.println(BillUtil.class.getResource(""));
		System.out.println(BillUtil.class.getResource("").getPath());
	}

}
