package com.fc.pay.controller.bill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.service.BillBizMistakeService;
import com.fc.pay.boss.enums.ErrorEnum;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.controller.base.BaseController;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.entity.PayRefundOrder;
import com.fc.pay.trade.service.merchant.IMerchantApp;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.service.trade.IRefundOrder;

/**
 * 对账差错管理
 * @author cjw
 *
 */
@Controller
@RequestMapping("billmistake")
public class BillMistakeController extends BaseController {
	
	@Autowired
	private BillBizMistakeService billBizMistakeService;
	
	@Autowired
	private IMerchantApp merchantApp;
	
	@Autowired
	private IRefundOrder iRefundOrder;
	@Autowired
	private IPaymentOrder iPaymentOrder;
	
	/**
	 * 对账批次界面
	 * @return
	 */
	@RequiresPermissions("bill:mistake:list")
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		List<MerchantApp> merchantApps =  merchantApp.selectMerchantApp();
		model.addAttribute("merchantApps", merchantApps);
		return "billmistake/list";
	}
	
	/**
	 * 商户列表数据
	 * @param request
	 * @param current
	 * @param size
	 * @return
	 */
	@RequiresPermissions("bill:mistake:list")
	@RequestMapping("listdata")
	@ResponseBody
	public String listData(HttpServletRequest request,String startDate,String endDate,Integer current,Integer size){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> paramMap = this.getRequestParams(request);
			Page page = billBizMistakeService.selectPageList(paramMap, current, size);
			result.put("data",page);
			result.put("total", page.getTotalItem());
			result.put("pages",page.getTotalPages());
		} catch (Exception e) {
			result.put("errcode", ErrorEnum.SERVER_ERROR.getCode());
			result.put("errmsg", ErrorEnum.SERVER_ERROR.getMsg());
			logger.error(e.getMessage(), e);
		}
		return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd");
	}
	
	/**
	 * 根据订单号查看差错详情
	 * @param request
	 * @param model
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest request,Model model,String orderNo){
		try {
			
			PayRefundOrder refundOrder = iRefundOrder.getByRefundNo(orderNo);
			if (StringUtils.isNotBlank(orderNo)) {
				Map<String, Object> paramMap = new HashMap<>(0);
				paramMap.put("refundOrderNo", orderNo);
				List<BillBizMistake> refundMistakes = billBizMistakeService.list(paramMap);//退款差错信息
				paramMap.clear();
				paramMap.put("payOrderNo", orderNo);
				List<BillBizMistake> payMistakes = billBizMistakeService.list(paramMap);//对账差错信息
				PayPaymentOrder paymentOrder = iPaymentOrder.getByOrderNo(orderNo);
				model.addAttribute("refundMistakes", refundMistakes);
				model.addAttribute("payMistakes", payMistakes);
				model.addAttribute("paymentOrder", paymentOrder);
			}
			model.addAttribute("refundOrder", refundOrder);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "billmistake/detail";
	}
}
