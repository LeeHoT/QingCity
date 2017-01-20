package com.qingcity.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 充值记录
 * @author leehotin
 *
 */
public class RechargeRecord implements Serializable{
	private static final long serialVersionUID = -620531829110450941L;
	private int id;
	private int playerId;
	private Timestamp recTime;//充值时间
	private String type;//充值类型  例如 充会员  冲金币，，钻石
	private Double money;// 充值金额

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public Timestamp getRecTime() {
		return recTime;
	}

	public void setRecTime(Timestamp recTime) {
		this.recTime = recTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

}
