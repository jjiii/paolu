package com.fc.pay.common.vo.bean;

public class PayChannel {
	
    private String merchantCode;

    private String merchantName;

    private String merchantAppCode;

    private String merchantAppName;

    private String channel;
    
    private Integer status;
    
    private String pic;//数据库没有这个字段

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantAppCode() {
		return merchantAppCode;
	}

	public void setMerchantAppCode(String merchantAppCode) {
		this.merchantAppCode = merchantAppCode;
	}

	public String getMerchantAppName() {
		return merchantAppName;
	}

	public void setMerchantAppName(String merchantAppName) {
		this.merchantAppName = merchantAppName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
    
}
