package com.fc.pay.geteway.controller.bill;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc.pay.bill.entity.BillBizFileNotify;
import com.fc.pay.bill.entity.BillBizMistake;
import com.fc.pay.bill.enums.BillMakeStatusEnum;
import com.fc.pay.bill.service.BillBizFileNotifyService;
import com.fc.pay.bill.service.BillBizMistakeService;
import com.fc.pay.bill.utils.BillUtil;
import com.fc.pay.bill.utils.DateUtil;
import com.fc.pay.common.core.enums.CodeEnum;
import com.fc.pay.common.core.enums.YesNoEnum;
import com.fc.pay.trade.utils.Return;

/**
 * 商户应用账单操作接口实现
 * @author zhanjq
 *
 */
@Controller
@RequestMapping(value = "/bill")
public class BillController {
	
	private static final Logger log = LoggerFactory.getLogger(BillController.class);
	
	@Autowired
	private BillBizMistakeService mistakeService;
	
	@Autowired
	private BillBizFileNotifyService fileNotifyService;

	/**
	 * 下载业务日对账单
	 * @param request
	 * @param response
	 * @param merchantCode		商户编号	
	 * @param merchantAppCode	商户应用编号
	 * @param billDate			账单日期	类型：字符串		数据格式：yyyyMMdd
	 * @throws Exception
	 */
	@RequestMapping(value="/file/download/zip")
	@ResponseBody
	public Return billDownload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="merchantAppCode",required=true) String merchantAppCode,
			@RequestParam(value="billDate",required=true) String billDate) {
		
		log.info("商户应用日账单下载开始>>>");
		//response.setHeader("Connection", "Keep-Alive");
		//log.info("set connection keepalive");
		//Keep-Alive: timeout=15, max=298连接的时长
		//response.setHeader("Keep-Alive", "timeout=150000, max=-1");;
		//log.info("set keepalive value");
		log.info("not set keep alive");
		
		/***/		
		Date billDateVal = null;
		try {
			billDateVal = DateUtil.parseDefaultDateContent(billDate);
		} catch (ParseException e) {
			log.info("账单日期解析错误"+e.getMessage());
			/** 错误提示[错误码，错误消息]  */
			return new Return(CodeEnum._00012.getName(), CodeEnum._00012.getValue(), null);
		}
		
		BillBizFileNotify fileNotify = fileNotifyService.getBy(merchantAppCode, billDateVal);
		if(fileNotify==null){
			return new Return(CodeEnum._00011.name(),CodeEnum._00011.getValue(),null);
		}
		if(!BillMakeStatusEnum.success.name().equals(fileNotify.getFileStatus())){
			log.info("账单状态错误"+fileNotify.getFileStatus());
			return new Return(CodeEnum._00011.name(),CodeEnum._00011.getValue(),null);
		}
		log.info("账单相对路径["+fileNotify.getFilePath()+"]");
		String billPath = BillUtil.readBillInsidePath()+fileNotify.getFilePath();
		log.info("账单绝对路径["+billPath+"]");
		/** 正常响应  */
		InputStream input = null;
		byte[] data = null;
		String fileData = null;
		try{
			input = new FileInputStream(new File(billPath));
			data = new byte[input.available()];
			input.read(data);
			fileData = Base64.encodeBase64String(data);			
		}catch(Exception e){
			log.error("下载业务日对账单错误["+e.getMessage()+"]");
			return new Return(CodeEnum._00013.getName(), CodeEnum._00013.getValue(), null);
		}finally{
			IOUtils.closeQuietly(input);
		}
		
		log.info("<<<商户应用日账单下载结束");
		
		HashMap<String, String> result = new HashMap<String, String>();
		result.put("fileData", fileData);
		Return rtn = new Return(CodeEnum._10000.getName(),CodeEnum._10000.getValue(), result);
		return rtn;
		
		/** mock start */
		/**
		log.info(" merchantAppCode=>"+merchantAppCode+" billDate=>"+billDate);
		
		String fileData = null;
		String mockBillPath = "F:/data/bill/daily/inside/01/0101/20161214/0101_20161214.csv.zip";
		byte[] data = null;
		InputStream inputMock = null;
		//OutputStream outputMock = null;
		try{
			inputMock = new FileInputStream(new File(mockBillPath));
			//outputMock = response.getOutputStream();	
			//IOUtils.copyLarge(inputMock, outputMock);//内部byte[]buffer缓冲区大小为4096字节
			data = new byte[inputMock.available()];
			inputMock.read(data);
			fileData = Base64.encodeBase64String(data);
		}catch(Exception e){
			log.error("下载业务日对账单错误["+e.getMessage()+"]");
		}finally{
			IOUtils.closeQuietly(inputMock);
			//IOUtils.closeQuietly(outputMock);
		}
		HashMap<String, String> result = new HashMap<String, String>();
		result.put("fileData", fileData);
		Return rtn = new Return(CodeEnum._10000.getName(),CodeEnum._10000.getValue(), result);
		return rtn;
		*/
		/** mock end */
	}
	
	/**
	 * 对账差错处理通知确认
	 * @param request
	 * @param response
	 * @param mistakeId
	 * @throws Exception
	 */
	@RequestMapping(value="/mistake/notify/confirm",method=RequestMethod.POST)
	public void billMistakeNotifyConfirm(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="mistakeId",required=true) Long mistakeId) throws Exception {
		
		log.info("对账差错处理通知确认开始>>>");
		
		/** ------------------验证签名--------------------*/
		
		try{
		/** 正常响应  */
		BillBizMistake mistake = mistakeService.get(mistakeId);		
		mistake.setNotifyStatus(YesNoEnum.yes.name());
		mistakeService.modify(mistake);
		}catch(Exception e){
			log.error("对账差错处理通知确认错误["+e.getMessage()+"]");
			/** 错误提示[错误码，错误消息]  */
		}
		
		log.info("<<<对账差错处理通知确认结束");
	}
	
}
