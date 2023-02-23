package com.fc.pay.bill.utils;

import java.io.File;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.fc.pay.bill.utils.ZipUtil;

public class ZipUtilTest {
	
	private static final Logger log = LoggerFactory.getLogger(ZipUtilTest.class);
	
	@Test
	public void unzipBillTest(){
		log.info("解压结果:"+ZipUtil.unzip("D:/drilldata/zip/merchant/20161118/fengche_20161118.zip", "C:/merchant明细汇总", Charset.forName("gbk")));
	}
	
	@Test
	public void zipBillTest(){
		try {
			log.info("压缩结果:"+ZipUtil.zip(new File("C:/merchant明细汇总"),new File("C:/账务明细%^%^&^&123456ABC.zip")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
