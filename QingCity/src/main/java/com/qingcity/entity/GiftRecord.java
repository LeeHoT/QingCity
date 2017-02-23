package com.qingcity.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author leehotin
 * @Date 2017年2月21日 下午7:57:59
 * @Description 奖品记录实体类
 */
public class GiftRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer type;
	private Integer userId;
	private Date winTime;
	private Integer status;// 状态-1.未中奖0.未抽奖1.已中奖

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getWinTime() {
		return winTime;
	}

	public void setWinTime(Date winTime) {
		this.winTime = winTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}