package com.fc.pay.controller.testbill;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.service.BillBizCheckTranxService;
import com.fc.pay.bill.service.BillBizMistakeService;
import com.fc.pay.boss.entity.BossOperator;
import com.fc.pay.boss.enums.ErrorEnum;
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
 * @Description: 退款管理控制层
 * @author rongyz 
 * @date 2016年12月27日 上午11:01:53 
 *
 */
@RequestMapping(value = "testbill/refund")
@Controller
public class RefundOrderController extends BaseController {
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
	
	
	@InitBinder("refundOrder")  
	public void initRefundBinder(WebDataBinder binder) {  
	    binder.setFieldDefaultPrefix("refundOrder.");  
	} 

	@InitBinder("paymentOrder")  
	public void initPayBinder(WebDataBinder binder) {  
	    binder.setFieldDefaultPrefix("paymentOrder.");  
	}

	/**
	 * order/refund/list
	 * 列表页
	 * @return
	 */
	@RequiresPermissions("test:refundorder:list")
	@RequestMapping(value = "list", method = { RequestMethod.POST, RequestMethod.GET })
	public String index(Model model) {
		List<MerchantApp> merchantApps = merchantApp.selectMerchantApp();
		model.addAttribute("merchantApps", merchantApps);
		return "testbill/refund/list";
	}

	/**
	 * order/refund/listdata
	 * 获取列表数据
	 * @return
	 */
	@RequiresPermissions("test:refundorder:list")
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
	 * 查看退款订单
	 * order/refund/detail
	 * @param id
	 * @return
	 */
	@RequiresPermissions("test:refundorder:edit")
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
		return "testbill/refund/detail";
	}
	
	/**
	 * 修改退款订单
	 * @param request
	 * @param payPaymentOrder
	 * @return
	 */
	@RequiresPermissions("test:refundorder:edit")
	@RequestMapping(value = "edit", method = { RequestMethod.POST})
	@ResponseBody
	public String edit(HttpServletRequest request,@ModelAttribute("refundOrder") PayRefundOrder refundOrder,@ModelAttribute("paymentOrder") PayPaymentOrder paymentOrder){
		Map<String,Object> info = new HashMap<>();
		int errcode = 0;
		String errmsg = "";
		try {
			BossOperator bossOperator = super.getCurrentOperator();
			refundOrder.setEditor(bossOperator.getRealName());
			refundOrder.setEditTime(new Date());
			paymentOrder.setEditor(bossOperator.getRealName());
			paymentOrder.setEditTime(new Date());
			int result = iRefundOrder.modify(refundOrder,paymentOrder);
			if(result>0){
				errmsg = ErrorEnum.SUCCESS.getMsg();
			}else{
				errcode = ErrorEnum.UPDATE_ERROR.getCode();
				errmsg = ErrorEnum.UPDATE_ERROR.getMsg();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			errcode = ErrorEnum.SERVER_ERROR.getCode();
			errmsg = ErrorEnum.SERVER_ERROR.getMsg();
		}
		info.put("errcode", errcode);
		info.put("errmsg", errmsg);
		return JSON.toJSONString(info);
	}

}
