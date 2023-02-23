package com.fc.pay.common.pay.alipay;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;

import com.fc.pay.common.core.utils.RSA;

/**
 * 该类用于调用支付宝接口的请求参数处理<br/>
 * 排序，加密生成数据sign
 * @author XDou
 */
public class AliParam {
	
	private AliConfig aliConfig = null;
	
	public AliParam(){
	}
	
	public AliParam(AliConfig aliConfig){
		this.aliConfig = aliConfig;
	}
	
	/**
	 * 构建请求支付的所有参数,只需要传入业务参数
	 * @注意：必须传入的字段为：method.否则抛运行时异常
	 * @param 传入业务参数即可
	 * @return 返回调用支付宝需要的所有参数
	 */
	public Map<String, String> build(Map<String, String> param) {
		String biz_content = this.bizContent(param);
		
		Map<String, String> all = this.commonParam(param);
		all.put("biz_content", biz_content);
		
		String sort = this.sort(all);
		all.put("sign", RSA.sign(sort, aliConfig.privateKey));
		return all;
	}

	
	private Map<String, String> commonParam(Map<String, String> param) {
		Map<String, String> url = new HashMap<String, String>();
		if(!param.containsKey("method")){
			throw new RuntimeException("method为必填字段");
		}
		url.put("charset", aliConfig.charset);
		url.put("method", param.get("method").toString());//必填字段
		url.put("version", aliConfig.version);
		url.put("app_id", aliConfig.app_id);
		url.put("sign_type", aliConfig.sign_type);
		url.put("timestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		url.put("alipay_sdk","alipay-sdk-java-dynamicVersionNo");
		url.put("format","json");
		url.put("notify_url", aliConfig.mobile_notifyUrl);
		
		return url;
	}
	

	/**
	 * 剔除map里的sign字段，并排序<br/>
	 * 返回string的的格式如：name=value&emil=value
	 * @param params
	 * @return
	 */
	private String sort(Map<String, String> param) {
		List<String> list = new ArrayList<String>(param.keySet());
		Collections.sort(list);
		StringBuffer str = new StringBuffer();
		int i = 0;
		for (String key : list) {
			if (!"sign".equals(key)) {
				str.append(key).append("=").append(param.get(key));
				i++;
				if (i < list.size()) {
					str.append("&");
				}
			}
		}
		return str.toString();
	}
	
	
	/**
	 * 构建请求支付URL后的请求参数
	 * 必须在请求地址后加参数，
	 * 如果使用整个from表单提交，会出现中文不支持的情况，同样的参数却会导致签名失败
	 * @param 传入业务参数，公共参数不需要传入
	 */
	public String buildUrl(Map<String, String> param) {
		return aliConfig.url + "?" + this.urlParam(param);
	}
	
//	public String buildBizContent(Map<String, String> param) {
//		String biz_content = this.bizContent(param);
//		return biz_content.replaceAll("\"", "&quot;");
//	}
	
	/**
	 * 构建请求业务参数biz_content字段
	 * 除去method字段，method字段为公共字段
	 * @param param
	 * @return
	 */
	public String bizContent(Map<String, String> param) {
		try {
			StringBuffer str = new StringBuffer();
			str.append("{");
			int i = 0;
			for (String key : param.keySet()) {
				if(!"method".equals(key)){
					str.append("\"").append(key).append("\"");
					str.append(":");
					if("extend_params".equals(key) ){//|| StringUtils.contains(key, "amount")
						str.append(param.get(key));
					}else{
						str.append("\"");
						str.append(param.get(key));
						str.append("\"");
					}
					
					i++;
					if (i < param.keySet().size()-1) {
						str.append(",");
					}
				}
			}
			str.append("}");
//			return URLEncoder.encode(str.toString(), "utf-8");
			return str.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public String urlParam(Map<String, String> param) {
		try {
			Map<String, String> urlParam =  this.build(param);
			StringBuilder parm = new StringBuilder();
			int i = 0;
			for(String key : urlParam.keySet()){
				i++;
				if(!"biz_content".equals(key)){
					parm.append(key).append("=").append(URLEncoder.encode(urlParam.get(key), "utf-8"));
				}else{
					continue;
				}
				
		        if(i<urlParam.size()){
		        	parm.append("&");
		        }
//		        System.out.println(parm.toString());
			}
			return parm.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
