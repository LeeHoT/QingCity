package com.qingcity.entity;

import java.io.Serializable;

/**
 * 背包 存放的物品数量，种类
 * 
 * @author Administrator
 *
 */
public class Backpack implements Serializable {
	private static final long serialVersionUID = -4501872790288999011L;
	private int id;
	private int playerId;
	private int itemId;
	private int count;// 物品数量

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
