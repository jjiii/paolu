package com.fc.pay.bill.filter;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * 微信退款明细账单过滤器
 * 
 * @author zhanjq
 *
 */
public class WeixinRefundItemBillFileFilter implements IOFileFilter {
	
	/**
	 * 参考文件名:
	 * 商户号_appID_yyyyMMdd_refund
	 */

	@Override
	public boolean accept(File pathname) {
		if(pathname.getName().endsWith("_refund")){
			return true;
		}
		return false;
	}

	@Override
	public boolean accept(File dir, String name) {
		return false;
	}

}
