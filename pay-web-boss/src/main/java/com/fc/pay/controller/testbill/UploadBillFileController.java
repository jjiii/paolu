package com.fc.pay.controller.testbill;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.fc.pay.bill.business.BillBizCheckBatchBusiness;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.service.BillBizCheckTranxService;
import com.fc.pay.bill.service.BillBizMistakeService;
import com.fc.pay.boss.entity.BossOperator;
import com.fc.pay.boss.enums.ErrorEnum;
import com.fc.pay.boss.enums.StatusEnum;
import com.fc.pay.common.core.utils.PathUtil;
import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.controller.base.BaseController;
import com.fc.pay.trade.entity.MerchantApp;
import com.fc.pay.trade.entity.MerchantAppConfig;
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
@RequestMapping(value = "testbill/uploadbillfile")
@Controller
public class UploadBillFileController extends BaseController {
	
	@Autowired
	private BillBizCheckBatchBusiness billCheckBatBusiness;

	@RequiresPermissions("test:bill:view")
	@RequestMapping("view")
	public String view(HttpServletRequest request){
		
		return "testbill/billfile/uploadbillfile";
	}
	
	/**
	 * 商户应用支付接口新增
	 * @return
	 */
	@RequiresPermissions("test:bill:upload")
	@RequestMapping("uploadbillfile")
	@ResponseBody
	public String addData(HttpServletRequest request,String channel,String channelMerchantId,String channelAppId, MultipartFile billfile,String billDate){
		Map<String,Object> info = new HashMap<>();
		int errcode = ErrorEnum.SUCCESS.getCode();
		String errmsg = ErrorEnum.SUCCESS.getMsg();
		try {
			if(StringUtils.isEmpty(channelMerchantId) || StringUtils.isEmpty(channelAppId) || billfile==null || StringUtils.isEmpty(billDate)){
				errcode = ErrorEnum.NO_DATA_ERROR.getCode();
				errmsg = "表单信息填写不整";
			}else{
				// 拼装路径
				StringBuffer childPath = new StringBuffer();
				if(channel.equals("alipay")){
					childPath.append("alipay/").append(channelAppId).append("/");
				}else if(channel.equals("weixin")){
					childPath.append("weixin/").append(channelMerchantId).append("/").append(channelAppId).append("/");
				}else if(channel.equals("unionpay")){
					childPath.append("unionpay/").append(channelMerchantId).append("/");
				}
				// 上传文件
				String filePath = upload(billfile,childPath.toString());
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				boolean result = billCheckBatBusiness.checkBatchForTestVerify(channel, channelMerchantId, channelAppId, sdf.parse(billDate), filePath);
				if(!result){
					
					errcode = ErrorEnum.SAVE_ERROR.getCode();
					errmsg = ErrorEnum.SAVE_ERROR.getMsg();
				}
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
	
	/**
	 * 上传文件
	 * @param request
	 * @param uploadFile
	 * @return
	 */
	private String upload(MultipartFile uploadFile,String childPath){
		try {
			
			if(uploadFile != null && !uploadFile.isEmpty()){
				String fileName = uploadFile.getOriginalFilename();
				String path = PathUtil.readBillBasicPath()+childPath; 
				File file = new File(path,fileName);
				if(!file.exists()){
					file.mkdirs();
				}
				uploadFile.transferTo(file);
				return childPath+fileName;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}
	
}
