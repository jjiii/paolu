package com.fc.pay.boss.entity;

import java.io.Serializable;
import java.util.Date;

public class BossOperatorLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**  */
	private Long id;

	/** 创建者 */
	private String creater;

	/** 创建时间 */
	private Date createTime;

	/** 修改人 */
	private String editor;

	/** 修改时间 */
	private Date editTime;

	/** 版本号 */
	private Integer version;

	/** 操作人id */
	private Long operatorId;

	/** 操作人姓名 */
	private String operatorName;

	/** 操作类型( 1:增加，2:修改，3:删除) */
	private String operateType;

	/** 地址ip */
	private String ip;

	/**  */
	private String content;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEditor() {
		return this.editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Date getEditTime() {
		return this.editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperateType() {
		return this.operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
