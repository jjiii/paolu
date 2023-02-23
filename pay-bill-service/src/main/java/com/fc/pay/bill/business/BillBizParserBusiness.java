package com.fc.pay.bill.business;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fc.pay.bill.entity.BillBizBatch;
import com.fc.pay.bill.parser.OutsideDailyBillParser;
import com.fc.pay.bill.vo.OutsideDailyBizBillItem;

/**
 * 业务对账单解析业务
 * 
 * @author zhanjq
 *
 */
@Service("billBizParserBusiness")
public class BillBizParserBusiness {
	
	private static final Logger log = LoggerFactory.getLogger(BillBizParserBusiness.class);
	
	@Autowired
	private BeanFactory beanFactory;
	
	public Map<String, List<OutsideDailyBizBillItem>> parseBizBill(BillBizBatch batch) throws Exception {
		log.debug("开始解析账单文件");
		// 根据支付方式得到解析器的名字
		String parserClassName = batch.getPayChannel() + "DailyBillParser";
		log.debug("根据支付方式得到解析器的名字[" + parserClassName + "]");
		OutsideDailyBillParser parser = (OutsideDailyBillParser) beanFactory.getBean(parserClassName);
		// 使用相应的解析器解析文件
		return parser.parseBizBill(batch);
	}

//	@Override
//	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//		this.beanFactory = beanFactory;
//	}

}
