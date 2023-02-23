package com.fc.pay.boss.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * 操作员与角色关联表; InnoDB free: 7168 kB
 *
 */
public class BossRoleOperator implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	private Long id;

	/** 创建人 */
	private String creater;

	/** 创建时间 */
	private Date createTime;

	/** 修改人 */
	private String editor;

	/** 修改时间 */
	private Date editTime;

	/**  */
	private String status;

	/**  */
	private String remark;

	/**  */
	private Long roleId;

	/**  */
	private Long operatorId;

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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

}
