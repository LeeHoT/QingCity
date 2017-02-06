package com.qingcity.entity;

import java.io.Serializable;

/**
 * 装扮
 * type 1 套装    2 上衣   3 裤子   4 鞋子  5 帽子
 * @author Administrator
 *
 */
public class Dress implements Serializable{
	private static final long serialVersionUID = 6021582427510960086L;
	private int id;
	private int type;//1代表套装    2代表上衣   3 代表裤子   4 代表 鞋子  5 代表帽子
	private String name;// 通过名字找到对应的服饰
	private String propertyCategory;//属性类别
	private int  increment;//属性增量

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPropertyCategory() {
		return propertyCategory;
	}

	public void setPropertyCategory(String propertyCategory) {
		this.propertyCategory = propertyCategory;
	}

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

}
