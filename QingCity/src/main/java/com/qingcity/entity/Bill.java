package com.qingcity.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author leehotin
 * @Date 2017年2月9日 下午4:02:31
 * @Description 订单实体类
 */
public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer orderId; // 订单号 : "System.currentTimeMillis()/1000"+userId
	private Integer pid; // 物品id
	private Date time; // 购买时间
	private Integer status; // 付款状态 1.已付款 2.待付款
	private Integer pNumber; // 购买数量
	private Double totalPrice; // 订单总价
	private Integer userId; // 购买玩家id

	public Integer getPid() {
		return pid;

	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getpNumber() {
		return pNumber;
	}

	public void setpNumber(Integer pNumber) {
		this.pNumber = pNumber;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}