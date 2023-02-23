package com.fc.notify.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.fc.pay.common.core.enums.TradeStatusEnum;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.service.trade.IPaymentOrder;

/**
 * 修改订单的状态为关闭:
 * 思路1：在关闭前去第三方查询一下，该订单是否已经成功支付，如果没有成功则关闭该订单
 * 思路2：直接修改订单状态为关闭
 * 
 * 一、实际上思路1遇到的问题：
 *  	1、第三方返回结果为未支付，然后系统关闭订单
 *  	这样操作后，用户依然可以在原第三方待支付的页面继续成功支付，因为没有调用第三方的关闭接口
 *  	2、加入调用第三关闭的接口你将面临的问题：
 *  		第一步：查询是否成功
 *  		第二步：调用关闭接口
 *  		第三步：修改订单状态为成功
 *  	3、你面临分布式事务，即：第一、二步受限于网络状态，而且三步要同时成功，	需要专门解决一致性问题。
 *  	即使可以解决一致性问题，也无法解决订单超时需要关闭的时候，	及时的，完全的用程序判断和解决状态问题
 *  	4、你面临不同渠道规则，修改订单状态为关闭业务甚至要比整个流程还要复杂
 *  	
 * 二、该类按照思路2解决：如果商户没有来查询这一订单，并且最终收不到第三方通知，出现的差错由对账来平账
 *  每隔10分钟，更新超时的订单状态
 * @author XDou
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class CloseOrderJob extends QuartzJobBean{
	
	@Autowired
	private IPaymentOrder iPaymentOrder;
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired 
	@Qualifier("jdbcTemplate_pay")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		String now = "'"+ DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "'";
		String sql = 
				  "update pay_payment_order set status='close', close_reason='超时系统关闭', edit_time=now() "
				+ "where status='pay_wait' and time_expire<="
				+ now +" limit 5000";
		
		jdbcTemplate.update(sql);
		
		
	}

}
