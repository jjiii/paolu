package com.fc.pay.geteway.controller.payway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fc.pay.common.core.enums.PayChannelEnum;
import com.fc.pay.common.core.utils.BeanUtil;
import com.fc.pay.common.vo.bean.PayChannel;
import com.fc.pay.trade.entity.MerchantAppConfig;
import com.fc.pay.trade.service.merchant.IMerchantAppConfig;
import com.fc.pay.trade.utils.Return;

@Controller
@RequestMapping(value = "/channel")
public class ChannelController {
	@Autowired
	private IMerchantAppConfig iMerchantAppConfig;

	@RequestMapping("/get")
	@ResponseBody
	public Return get(HttpServletRequest request,
			@RequestParam(required = true) String merchantAppCode) {

		List<MerchantAppConfig> list = iMerchantAppConfig.list(merchantAppCode);

		List<PayChannel> ways = new ArrayList<PayChannel>();

		for (MerchantAppConfig config : list) {

			PayChannel view = new PayChannel();
			BeanUtil.copyProperties(view, config);
			if (PayChannelEnum.alipay.name().equals(config.getChannel())) {
				//view.setPic(request.getContextPath()+"/pic/alipay.png");
				view.setPic("/pic/alipay.png");
			}
			if (PayChannelEnum.weixin.name().equals(config.getChannel())) {
				//view.setPic(request.getContextPath()+"/pic/weixin.png");
				view.setPic("/pic/weixin.png");
			}
			if (PayChannelEnum.unionpay.name().equals(config.getChannel())) {
				//view.setPic(request.getContextPath()+"/pic/unionpay.png");
				view.setPic("/pic/unionpay.png");
			}
			ways.add(view);
		}
		Map result = new HashMap();
		result.put("channel", ways);
		return new Return(result);

	}
}
