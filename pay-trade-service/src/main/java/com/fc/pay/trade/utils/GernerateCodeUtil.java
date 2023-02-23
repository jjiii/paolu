package com.fc.pay.trade.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GernerateCodeUtil {

	/**
	 * 生成编号
	 * @param maxCode 最大编号
	 * @param length 生成编号的长度
	 * @return
	 */
	public static String generateCode(String maxCode,String preChar,int length){
		String newCode = "";
		// 创建编号前缀
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String codePfix = preChar + format.format(new Date());
		// 最大编号不为空，并且最大编号包含以上编号前缀，说明为同一天，则在最大编号后累加，否则从1开始
		if (maxCode != null && maxCode.contains(codePfix)) {
			// 截取字符串最后几位
            String codeEnd = maxCode.substring(maxCode.length()-length, maxCode.length()); 
            // 把String类型的0001转化为int类型的1
            int endNum = Integer.parseInt(codeEnd); 
            newCode = codePfix+appendNum(endNum,length);
	    } else {
	    	newCode = codePfix + appendNum(0,length);
	    }
		
		return newCode;
	}
	
	private static String appendNum(int num,int length){
		StringBuffer zeroSb = new StringBuffer();
		for(int i=0; i<length; i++){
			zeroSb.append(0);
		}
		DecimalFormat df=new DecimalFormat(zeroSb.toString());
		int newNum = num + 1;
		
		return df.format(newNum);
	}
}
