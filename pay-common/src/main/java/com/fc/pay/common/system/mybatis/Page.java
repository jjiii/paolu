package com.fc.pay.common.system.mybatis;

import java.util.ArrayList;
/**
 * 分页对象,
 * 由于mybatis分页插件的机制限制，只能返回List类型，
 * 所以继承ArrayList
 * @author XDou
 * 2015年7月24日下午3:53:56
 */
public class Page extends ArrayList<Object> {
	private static final long serialVersionUID = 1L;

	private int totalItem = 0;// 总记录数.

	private int pageSize = 20;// 每页大小

	private Integer currNum = 1;// 当前页
	
	private Integer totalPages;

	//是否还有上一页
	public boolean hasPre() {
		return currNum > 1;
	}
	//是否还有下一页
	public boolean hasNext() {
		return (currNum + 1 <= getTotalPages());
	}
	
	//获取上一页
	public int preNum() {
		if (hasPre()) return currNum - 1;
		return 1;
	}
	//获取下一页
	public int nextNum() {
		if (hasNext()) return currNum + 1;
		return currNum;
	}

	//一共几页
	public int totalPages() {
		return (int) Math.ceil((double) totalItem / pageSize);
	}

	//当前页第一条记录位置, 序号从0开始.
	public int strartItem() {
		return (currNum - 1) * pageSize;
	}

	//当前页最后一条记录的位置
	public int endItem() {
		return (currNum - 1) * pageSize + pageSize;
	}

	//如 7,8, '9' ,10,11
	public int[] getSlider() {
		int count = 9;
		int halfSize = count / 2;
		int totalPage = getTotalPages();

		int startPageNo = Math.max(currNum - halfSize, 1);// 开始页
		int endPageNo = Math.min(startPageNo + count - 1, totalPage);// 结束页

		if (endPageNo - startPageNo < count - 1) { // 不足5项
			startPageNo = Math.max(endPageNo - count + 1, 1);
		}

		int[] slider = new int[endPageNo - startPageNo + 1];// sleder不会为空
		for (int i = 0; i <= slider.length - 1; i++) {
			slider[i] = startPageNo;
			startPageNo++;
		}
		return slider;

	}
	

	public int getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCurrNum() {
		return currNum;
	}
	public void setCurrNum(Integer currNum) {
		if(currNum >= 1){
			this.currNum = currNum;
		}
	}
	public Integer getTotalPages() {
		return (int) Math.ceil((double) totalItem / pageSize);
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

}
