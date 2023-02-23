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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.service.BillBizCheckTranxService;
import com.fc.pay.bill.service.BillBizMistakeService;
import com.fc.pay.boss.entity.BossOperator;
import com.fc.pay.boss.enums.ErrorEnum;
import com.fc.pay.boss.enums.StatusEnum;
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
@RequestMapping(value = "testbill/pay")
@Controller
public class PayOrderController extends BaseController {

	@Autowired
	private IPaymentOrder iPaymentOrder;
	@Autowired
	private BillBizMistakeService billBizMistakeService;
	@Autowired
	private BillBizCheckTranxService billBizCheckTranxService;
	@Autowired
	private IMerchantApp merchantApp;

	/**
	 * testbill/pay/list
	 * 支付订单列表页
	 * @return
	 */
	@RequiresPermissions("test:payorder:list")
	@RequestMapping(value = "list", method = { RequestMethod.POST, RequestMethod.GET })
	public String list(Model model) {
		List<MerchantApp> merchantApps = merchantApp.selectMerchantApp();
		model.addAttribute("merchantApps", merchantApps);
		return "testbill/pay/list";
	}

	/**
	 * testbill/pay/listdata
	 * 支付订单获取列表数据
	 * @return
	 */
	@RequiresPermissions("test:payorder:list")
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
	 * testbill/pay/detail
	 * 修改支付订单界面
	 * @param id	当前当前帐目ID
	 * @return
	 */
	@RequiresPermissions("test:payorder:edit")
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
		return "testbill/pay/detail";
	}

	/**
	 * 修改支付订单
	 * @param request
	 * @param payPaymentOrder
	 * @return
	 */
	@RequiresPermissions("test:payorder:edit")
	@RequestMapping(value = "edit", method = { RequestMethod.POST})
	@ResponseBody
	public String edit(HttpServletRequest request,PayPaymentOrder payPaymentOrder){
		Map<String,Object> info = new HashMap<>();
		int errcode = 0;
		String errmsg = "";
		try {
			BossOperator bossOperator = super.getCurrentOperator();
			payPaymentOrder.setEditor(bossOperator.getRealName());
			payPaymentOrder.setEditTime(new Date());
			int result = iPaymentOrder.modify(payPaymentOrder);
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
