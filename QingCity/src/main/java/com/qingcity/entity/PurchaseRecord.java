package com.qingcity.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 购买记录
 * @author Administrator
 *
 */
public class PurchaseRecord implements Serializable{
	private static final long serialVersionUID = 6915213264969600523L;
	private int id;
	private int playerId;
	private Timestamp purchaseTime;//购买时间
	private int itemId;//购买物品id
	private int count;// 购买数量

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

	public Timestamp getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(Timestamp purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	

}
