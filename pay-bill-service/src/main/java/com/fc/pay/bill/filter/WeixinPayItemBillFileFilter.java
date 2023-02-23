package com.fc.pay.bill.filter;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * 微信支付明细账单过滤器
 * 
 * @author zhanjq
 *
 */
public class WeixinPayItemBillFileFilter implements IOFileFilter {
	
	/**
	 * 参考文件名:
	 * 商户号_appID_yyyyMMdd_pay
	 */

	@Override
	public boolean accept(File pathname) {
		if(pathname.getName().endsWith("_pay")){
			return true;
		}
		return false;
	}

	@Override
	public boolean accept(File dir, String name) {
		return false;
	}

}
