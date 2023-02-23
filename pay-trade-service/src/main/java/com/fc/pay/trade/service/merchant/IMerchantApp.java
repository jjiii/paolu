package com.fc.pay.trade.service.merchant;

import java.util.List;
import java.util.Map;

import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.entity.MerchantApp;

public interface IMerchantApp {
	
	public int add(MerchantApp entity);

	public int delete(Long id);

	public int modify(MerchantApp entity);

	public MerchantApp get(Long id);

	public Page page(Map<String, Object> parm);

	/**
	 * 如果一条结果都没有，返回null
	 */
	public List<MerchantApp> list(Map<String, Object> parm);
	
	public MerchantApp get(Map<String, Object> parm);
	
	public MerchantApp getByMerchantAppCode(String merchantAppCode);
	
	/**
	 * 生成应用编码
	 * @return
	 */
	String generateCode();
	
	/**
	 * 检测编码是否存在
	 * @param merchantCode
	 * @return
	 */
	boolean codeIsExists(String merchantCode);
	
	/**
	 * 查询商户应用
	 * @return
	 */
	List<MerchantApp> selectMerchantApp();

}