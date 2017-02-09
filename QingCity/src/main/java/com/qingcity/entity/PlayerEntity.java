package com.qingcity.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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
	private int userId; // userEntity 外键，也是主键
	private String nickname;// 玩家昵称
	private Integer level;// 玩家等级
	private Integer experience;// 玩家当前等级拥有的经验，假设玩家该等级需要总经验值为100*玩家等级
	private int power; // 用户体力
	private int diamond; // 用户钻石
	private int gold; // 用户金币
	private Timestamp lastPowUpdateTime;// 上次体力更新时间

	private String icon;// 头像名称
	private int contribution;// 玩家在公会中的贡献
	private int societyId;// 公会Id
	private int job;// 公会职位 1为会长，2为副会长，3位成员

	public int getJob() {
		return job;
	}

	public void setJob(int job) {
		this.job = job;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getContribution() {
		return contribution;
	}

	public void setContribution(int contribution) {
		this.contribution = contribution;
	}

	public int getSocietyId() {
		return societyId;
	}

	public void setSocietyId(int societyId) {
		this.societyId = societyId;
	}

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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public Timestamp getLastPowUpdateTime() {
		return lastPowUpdateTime;
	}

	public void setLastPowUpdateTime(Timestamp lastPowUpdateTime) {
		this.lastPowUpdateTime = lastPowUpdateTime;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public static void main(String[] args) {
		String str = "孟仁杰";
		System.out.println(str.length());
	}

}
