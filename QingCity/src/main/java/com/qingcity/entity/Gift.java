package com.qingcity.entity;

import java.io.Serializable;

/**
 * 
 * @author leehotin
 * @Date 2017年2月21日 下午7:58:31
 * @Description 奖品类
 */
public class Gift implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer count;
	private String notes;
	private Integer type;

	public Gift() {
	}

	public Gift(Integer id, String name, Integer count, String notes) {
		super();
		this.id = id;
		this.name = name;
		this.count = count;
		this.notes = notes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes == null ? null : notes.trim();
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}