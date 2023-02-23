package com.fc.pay.boss.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 数据字典
 * @author java1
 *
 */
public class BossDictionary implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2141372772679912023L;

	private Long id;

    private String code;

    private String name;

    private Long parent;

    private String description;

    private Integer orderby;
    
    private List<BossDictionary> childs;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }

	public List<BossDictionary> getChilds() {
		return childs;
	}

	public void setChilds(List<BossDictionary> childs) {
		this.childs = childs;
	}
    
}