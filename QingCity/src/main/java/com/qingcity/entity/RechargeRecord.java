package com.qingcity.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author leehotin
 * @Date 2017年2月9日 下午4:12:45
 * @Description 充值记录
 */
public class RechargeRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String rechargeId; // 充值账单id
	private Integer userId; // 玩家id
	private Integer pid; // 充值物品id
	private Date time; // 充值时间
	private Integer status; // 充值状态
	private Integer pNumber; // 充值物品的数量
	private Double totalPrice; // 本次充值总价

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

	public String getRechargeId() {
		return rechargeId;
	}

	public void setRechargeId(String rechargeId) {
		this.rechargeId = rechargeId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

}