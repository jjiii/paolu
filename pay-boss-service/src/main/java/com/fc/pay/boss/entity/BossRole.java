package com.fc.pay.boss.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * 角色表; InnoDB free: 7168 kB
 *
 */
public class BossRole implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	private Long id;

	private Long version;

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

	/** 角色类型（1:超级管理员角色，0:普通操作员角色） */
	private String roleCode;

	/**  */
	private String roleName;
	
	private int isChecked=0;	// 0表示未有用户绑定该角色
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
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

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}

}
