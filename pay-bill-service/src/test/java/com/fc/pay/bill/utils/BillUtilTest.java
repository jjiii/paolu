package com.fc.pay.bill.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.fc.pay.common.core.utils.PathUtil;

public class BillUtilTest {
	
	private static final Logger log = LoggerFactory.getLogger(BillUtilTest.class);

	@Test
	public void test(){
		
		log.info("-------------------------- 打印证书基础路径 ---------------------------");
		log.info("CertBasicPath=>"+PathUtil.readCertBasicPath());
		
		log.info("-------------------------- 打印账单基础路径 ---------------------------");
		log.info("BillBasicPath=>"+BillUtil.readBillConfig("BillBasicPath"));
		log.info("BillOutsidePath=>"+BillUtil.readBillConfig("BillOutsidePath"));
		log.info("BillInsidePath=>"+BillUtil.readBillConfig("BillInsidePath"));

		log.info("-------------------------- 外部渠道日账单 ---------------------------");
//		log.info("银联渠道=>"+BillUtil.makeOutsideDailyBillDateDirExist("unionpay","mch001","app001","20161115"));		
//		log.info("微信渠道=>"+BillUtil.makeOutsideDailyBillDateDirExist("weixin","mch002","app002","20161116"));
//		log.info("支付宝渠道=>"+BillUtil.makeOutsideDailyBillDateDirExist("alipay","mch003","app003","20161117"));
		
		log.info("-------------------------- 外部渠道日账单 ---------------------------");
//		log.info("商城应用=>"+BillUtil.makeInsideDailyBillDateDirExist("fc","shagncheng","20161115"));		
//		log.info("快手应用=>"+BillUtil.makeInsideDailyBillDateDirExist("fc","kuaishou","20161116"));
		
		
		log.info("-------------------------- 支付宝交易 ---------------------------");
		log.info("AlipayTranTagPay=>"+BillUtil.readAlipayTranTagPay());
		log.info("AlipayTranTagRefund=>"+BillUtil.readAlipayTranTagRefund());
		
		log.info("-------------------------- 微信交易 ---------------------------");
		log.info("WeixinTranTagPay=>"+BillUtil.readWeixinTranTagPay());
		log.info("WeixinTranTagRefund=>"+BillUtil.readWeixinTranTagRefund());
		
		log.info("-------------------------- 银联交易 ---------------------------");
		log.info("UnionpayTranTagPay=>"+BillUtil.readUnionpayTranTagPay());
		log.info("UnionpayTranTagRefund=>"+BillUtil.readUnionpayTranTagRefund());
		
	}
	
}
