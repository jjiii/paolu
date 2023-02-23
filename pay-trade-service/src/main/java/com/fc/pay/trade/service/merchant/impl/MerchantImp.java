package com.fc.pay.trade.service.merchant.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fc.pay.common.system.mybatis.Page;
import com.fc.pay.trade.dao.MerchantMapper;
import com.fc.pay.trade.entity.Merchant;
import com.fc.pay.trade.service.merchant.IMerchant;
import com.fc.pay.trade.utils.GernerateCodeUtil;
@Service
public class MerchantImp implements IMerchant{
	
	@Autowired
	private MerchantMapper merchantMapper;
	
	@Transactional
	@Override
	public int add(Merchant record) {
		
		return merchantMapper.insertSelective(record);
	}

	@Transactional
	@Override
	public int delete(Long id) {
		return merchantMapper.deleteByPrimaryKey(id);
	}

	@Transactional
	@Override
	public int modify(Merchant record) {
		return merchantMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Merchant get(Long id) {
		return merchantMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page page(Map<String, Object> parm,Integer current,Integer pagesize) {
		Page page = new Page();
		page.setCurrNum(current);
		page.setPageSize(pagesize);
		parm.put("page", page);
		page = merchantMapper.pageList(parm);
		return page;
	}

	@Override
	public List<Merchant> list(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Merchant get(Map<String, Object> parm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateCode() {
		String newCode = "";
		// 鏁版嵁璁板綍涓殑鏈�澶х紪鍙�
		String maxCode = merchantMapper.selectMaxCode();
		newCode = GernerateCodeUtil.generateCode(maxCode, "M", 5);
		return newCode;
	}

	@Override
	public boolean codeIsExists(String merchantCode) {
		
		return merchantMapper.selectCountCode(merchantCode)>0;
	}


}
