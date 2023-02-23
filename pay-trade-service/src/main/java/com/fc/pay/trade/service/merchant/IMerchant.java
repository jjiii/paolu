package com.fc.pay.trade.service.merchant;

import java.util.List;
import java.util.Map;

import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.entity.Merchant;


public interface IMerchant  {
	
	public int add(Merchant record);
	
	public int delete(Long id);
	
	public int modify(Merchant record);
	
	public Merchant get(Long id);

	public Page page(Map<String, Object> parm,Integer current,Integer pagesize);
	
	public List<Merchant> list(Map<String, Object> parm);
	
	public Merchant get(Map<String, Object> parm);
	
	String generateCode();
	
	boolean codeIsExists(String merchantCode);
	
}