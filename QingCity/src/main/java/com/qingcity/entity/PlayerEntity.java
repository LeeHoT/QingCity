package com.qingcity.entity;

import java.io.Serializable;

/**
 * PlayerEntity 实体类 存放玩家各类属性信息
 * 
 * 对应player表，表中每个用户只能有一条数据
 * 
 * @author leehoting
 *
 */

public class PlayerEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private int playerId; // userEntity 外键，也是主键
	private Integer level;// 玩家等级
	private Integer experience;// 玩家当前等级拥有的经验，假设玩家该等级需要总经验值为100*玩家等级
	private int power; // 用户体力
	private int diamond; // 用户钻石
	private int gold; // 用户金币

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getDiamond() {
		return diamond;
	}

	public void setDiamond(int diamond) {
		this.diamond = diamond;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

}
