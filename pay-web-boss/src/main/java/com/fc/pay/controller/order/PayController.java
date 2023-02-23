package com.fc.pay.controller.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.service.BillBizCheckTranxService;
import com.fc.pay.bill.service.BillBizMistakeService;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.controller.base.BaseController;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.entity.PayPaymentOrder;
import com.fc.pay.trade.service.merchant.IMerchantApp;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.utils.PageData;

/**
 * 
 * @ClassName: PayController 
 * @Description: 支付订单管理控制层
 * @author rongyz 
 * @date 2016年12月27日 上午11:00:37 
 *
 */
@RequestMapping(value = "order/pay")
@Controller
public class PayController extends BaseController {

	@Autowired
	private IPaymentOrder iPaymentOrder;
	@Autowired
	private BillBizMistakeService billBizMistakeService;
	@Autowired
	private BillBizCheckTranxService billBizCheckTranxService;
	@Autowired
	private IMerchantApp merchantApp;

	/**
	 * order/pay/list
	 * 支付订单列表页
	 * @return
	 */
	@RequiresPermissions("order:pay:list")
	@RequestMapping(value = "list", method = { RequestMethod.POST, RequestMethod.GET })
	public String list(Model model) {
		List<MerchantApp> merchantApps = merchantApp.selectMerchantApp();
		model.addAttribute("merchantApps", merchantApps);
		return "order/pay/list";
	}

	/**
	 * order/pay/listdata
	 * 支付订单获取列表数据
	 * @return
	 */
	@RequiresPermissions("order:pay:list")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "listdata", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String listData() {
		Map<String, Object> result = new HashMap<String, Object>();
		PageData parm = getPageData();
		parm.put(parm.get("searchKey"), parm.get("searchValue"));
		Page page = iPaymentOrder.pageByMap(parm);
		result.put("data", page);
		result.put("total", page.getTotalItem());
		result.put("pages", page.getTotalPages());
		return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd");
	}

	/**
	 * order/pay/dosettle
	 * 支付订单确认平帐
	 * @param mistakeId		差错记录ID
	 * @param handleWay		差错处理方式:以支付渠道为准、以支付系统为准
	 * @param handleRemark	操作员填入处理说明
	 * @return
	 */
	@RequiresPermissions("order:pay:settle")
	@RequestMapping(value = "dosettle", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public boolean doSettle(String mistakeId, String handleType, String handleRemark) {
		try {
			billBizCheckTranxService.handleMistake(this.getCurrentOperator().getLoginName(), mistakeId, handleType,
					handleRemark);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * order/pay/detail
	 * 支付订单查看帐户
	 * @param id	当前当前帐目ID
	 * @return
	 */
	@RequiresPermissions("order:pay:view")
	@RequestMapping(value = "detail", method = { RequestMethod.POST, RequestMethod.GET })
	public String detail(Model model, Long id) {
		PayPaymentOrder payPaymentOrder = iPaymentOrder.get(id);
		String orderNo = payPaymentOrder.getOrderNo();//订单号
		Map<String, Object> paramMap = new HashMap<>(0);
		if (StringUtils.isNotBlank(orderNo)) {
			paramMap.put("payOrderNo", orderNo);
			List<BillBizMistake> mistakes = billBizMistakeService.list(paramMap);
			model.addAttribute("mistakes", mistakes);
		}
		model.addAttribute("data", payPaymentOrder);
		return "order/pay/detail";
	}

	/**************************************** 对账  ****************************************/

	/**
	 * order/pay/reconcilelist
	 * 支付对账管理列表
	 * @param model
	 * @return
	 */
	@RequiresPermissions("order:pay:reconcilelist")
	@RequestMapping(value = "reconcilelist", method = { RequestMethod.POST, RequestMethod.GET })
	public String reconcileList(Model model) {
		List<MerchantApp> merchantApps = merchantApp.selectMerchantApp();
		model.addAttribute("merchantApps", merchantApps);
		return "order/pay/reconcilelist";
	}

	/**
	 * order/pay/reconcilelistdata
	 * 支付对账获取列表数据
	 * @return
	 */
	@RequiresPermissions("order:pay:reconcilelist")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "reconcilelistdata", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String reconcileListData() {
		Map<String, Object> result = new HashMap<String, Object>();
		PageData parm = getPageData();
		parm.put(parm.get("searchKey"), parm.get("searchValue"));
		Page page = iPaymentOrder.pageByMap(parm);
		result.put("data", page);
		result.put("pages", page.getTotalPages());
		return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * order/pay/doreconcilesettle
	 * 支付对账确认平帐
	 * @param mistakeId		差错记录ID
	 * @param handleWay		差错处理方式:以支付渠道为准、以支付系统为准
	 * @param handleRemark	操作员填入处理说明
	 * @return
	 */
	@RequiresPermissions("order:pay:reconcilesettle")
	@RequestMapping(value = "doreconcilesettle", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public boolean doReconcileSettle(String mistakeId, String handleType, String handleRemark) {
		try {
			billBizCheckTranxService.handleMistake(this.getCurrentOperator().getLoginName(), mistakeId, handleType,
					handleRemark);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * order/pay/reconciledetail
	 * 支付对账查看帐户
	 * @param id	当前当前帐目ID
	 * @return
	 */
	@RequiresPermissions("order:pay:reconcileview")
	@RequestMapping(value = "reconciledetail", method = { RequestMethod.POST, RequestMethod.GET })
	public String reconcileDetail(Model model, Long id) {
		PayPaymentOrder payPaymentOrder = iPaymentOrder.get(id);
		String orderNo = payPaymentOrder.getOrderNo();//订单号
		Map<String, Object> paramMap = new HashMap<>(0);
		if (StringUtils.isNotBlank(orderNo)) {
			paramMap.put("payOrderNo", orderNo);
			List<BillBizMistake> mistakes = billBizMistakeService.list(paramMap);
			model.addAttribute("mistakes", mistakes);
		}
		model.addAttribute("data", payPaymentOrder);
		return "order/pay/reconciledetail";
	}

}
