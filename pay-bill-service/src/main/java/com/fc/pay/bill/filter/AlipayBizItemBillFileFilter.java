package com.fc.pay.bill.filter;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * 支付宝业务明细账单过滤器
 * 
 * @author zhanjq
 *
 */
public class AlipayBizItemBillFileFilter implements IOFileFilter {
	
	/**
	 * 参考文件名:
	 * 20881021788626270156_20161117_业务明细.csv
	 */

	@Override
	public boolean accept(File pathname) {
		/*
		if(pathname.getName().contains("(")){
			return true;
		}
		*/
		if(pathname.getName().endsWith("业务明细.csv")){
			return true;
		}
		return false;
	}

	@Override
	public boolean accept(File dir, String name) {
		return false;
	}

}
