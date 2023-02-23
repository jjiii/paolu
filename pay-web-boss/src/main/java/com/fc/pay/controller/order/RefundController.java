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
import com.fc.pay.trade.entity.PayRefundOrder;
import com.fc.pay.trade.service.merchant.IMerchantApp;
import com.fc.pay.trade.service.trade.IPaymentOrder;
import com.fc.pay.trade.service.trade.IRefundOrder;
import com.fc.pay.utils.PageData;

/**
 * 
 * @ClassName: RefundController 
 * @Description: 退款管理控制层
 * @author rongyz 
 * @date 2016年12月27日 上午11:01:53 
 *
 */
@RequestMapping(value = "order/refund")
@Controller
public class RefundController extends BaseController {
	@Autowired
	private IRefundOrder iRefundOrder;
	@Autowired
	private IPaymentOrder iPaymentOrder;
	@Autowired
	private BillBizMistakeService billBizMistakeService;
	@Autowired
	private BillBizCheckTranxService billBizCheckTranxService;
	@Autowired
	private IMerchantApp merchantApp;

	/**
	 * order/refund/list
	 * 列表页
	 * @return
	 */
	@RequiresPermissions("order:refund:list")
	@RequestMapping(value = "list", method = { RequestMethod.POST, RequestMethod.GET })
	public String index(Model model) {
		List<MerchantApp> merchantApps = merchantApp.selectMerchantApp();
		model.addAttribute("merchantApps", merchantApps);
		return "order/refund/list";
	}

	/**
	 * order/refund/listdata
	 * 获取列表数据
	 * @return
	 */
	@RequiresPermissions("order:refund:list")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "listdata", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String listData() {
		Map<String, Object> result = new HashMap<String, Object>();
		PageData parm = getPageData();
		Page page = iRefundOrder.page(parm);
		result.put("data", page);
		result.put("total", page.getTotalItem());
		result.put("pages", page.getTotalPages());
		return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 确认平帐
	 * order/refund/dosettle
	 * @param mistakeId		差错记录ID
	 * @param handleWay		差错处理方式:以支付渠道为准、以支付系统为准
	 * @param handleRemark	操作员填入处理说明
	 * @return
	 */
	@RequiresPermissions("order:refund:settle")
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
	 * 查看帐户
	 * order/refund/detail
	 * @param id	当前当前帐目ID
	 * @return
	 */
	@RequiresPermissions("order:refund:view")
	@RequestMapping(value = "detail", method = { RequestMethod.POST, RequestMethod.GET })
	public String detail(Model model, Long id) {
		PayRefundOrder refundOrder = iRefundOrder.get(id);
		String orderNo = refundOrder.getOrderNo();//原订单号
		if (StringUtils.isNotBlank(orderNo)) {
			Map<String, Object> paramMap = new HashMap<>(0);
			paramMap.put("refundOrderNo", orderNo);
			List<BillBizMistake> refundMistakes = billBizMistakeService.list(paramMap);//退款差错信息
			paramMap.clear();
			paramMap.put("payOrderNo", orderNo);
			List<BillBizMistake> payMistakes = billBizMistakeService.list(paramMap);//对账差错信息
			PayPaymentOrder paymentOrder = iPaymentOrder.getByOrderNo(orderNo);
			model.addAttribute("refundMistakes", refundMistakes);
			model.addAttribute("paymentOrder", payMistakes);
			model.addAttribute("paymentOrder", paymentOrder);
		}
		model.addAttribute("refundOrder", refundOrder);
		return "order/refund/detail";
	}

	/**************************************** 对账  ****************************************/
	/**
	 * order/refund/reconcilelist
	 * 对账列表页
	 * @return
	 */
	@RequiresPermissions("order:refund:reconcilelist")
	@RequestMapping(value = "reconcilelist", method = { RequestMethod.POST, RequestMethod.GET })
	public String reconcileIndex(Model model) {
		List<MerchantApp> merchantApps = merchantApp.selectMerchantApp();
		model.addAttribute("merchantApps", merchantApps);
		return "order/refund/reconcilelist";
	}

	/**
	 * order/refund/reconcilelistdata
	 * 对账获取列表数据
	 * @return
	 */
	@RequiresPermissions("order:refund:reconcilelist")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "reconcilelistdata", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String reconcileListData() {
		Map<String, Object> result = new HashMap<String, Object>();
		PageData parm = getPageData();
		Page page = iRefundOrder.page(parm);
		result.put("data", page);
		result.put("pages", page.getTotalPages());
		return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * order/refund/doreconcilesettle
	 * 对账确认平帐
	 * @param mistakeId		差错记录ID
	 * @param handleWay		差错处理方式:以支付渠道为准、以支付系统为准
	 * @param handleRemark	操作员填入处理说明
	 * @return
	 */
	@RequiresPermissions("order:refund:reconcilesettle")
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
	 * order/refund/reconciledetail
	 * 对账查看帐户
	 * @param id	当前当前帐目ID
	 * @return
	 */
	@RequiresPermissions("order:refund:reconcileview")
	@RequestMapping(value = "reconciledetail", method = { RequestMethod.POST, RequestMethod.GET })
	public String reconcileDetail(Model model, Long id) {
		PayRefundOrder refundOrder = iRefundOrder.get(id);
		String orderNo = refundOrder.getOrderNo();//原订单号
		if (StringUtils.isNotBlank(orderNo)) {
			Map<String, Object> paramMap = new HashMap<>(0);
			paramMap.put("refundOrderNo", orderNo);
			List<BillBizMistake> refundMistakes = billBizMistakeService.list(paramMap);//退款差错信息
			paramMap.clear();
			paramMap.put("payOrderNo", orderNo);
			List<BillBizMistake> payMistakes = billBizMistakeService.list(paramMap);//对账差错信息
			PayPaymentOrder paymentOrder = iPaymentOrder.getByOrderNo(orderNo);
			model.addAttribute("refundMistakes", refundMistakes);
			model.addAttribute("paymentOrder", payMistakes);
			model.addAttribute("paymentOrder", paymentOrder);
		}
		model.addAttribute("refundOrder", refundOrder);
		return "order/refund/reconciledetail";
	}

}
