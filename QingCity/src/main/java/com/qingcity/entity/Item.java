package com.qingcity.entity;

import java.io.Serializable;

/**
 * 
 * @author leehotin
 * @Date 2017年2月9日 下午4:19:29
 * @Description 物品实体类
 */
public class Item implements Serializable {
	private static final long serialVersionUID = -5975695654942127235L;
	private int id;// 物品id
	private String name; // 物品名称
	private Double price;// 物品单价

}
