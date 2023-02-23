package com.fc.pay.bill.filter;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * 银联支付业务明细账单过滤器
 * 
 * @author zhanjq
 *
 */
public class UnionpayBizItemBillFileFilter implements IOFileFilter {
	
	/**
	 * 参考文件名:
	 * INN16011988ZM_898111472980125
	 */

	@Override
	public boolean accept(File pathname) {
		String fileName = pathname.getName();
		if(fileName.startsWith("INN") && fileName.contains("ZM_")){
			return true;
		}
		return false;
	}

	@Override
	public boolean accept(File dir, String name) {
		return false;
	}

}
