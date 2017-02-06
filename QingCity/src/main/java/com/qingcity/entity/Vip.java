package com.qingcity.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Vip implements Serializable{

	private static final long serialVersionUID = 8369638617360288579L;
	private int playerId;//玩家id
	private int level;//会员等级
	private int experience;//会员经验
	private String  privilege;//会员特权
	private Timestamp begin; //会员开始时间
	private Timestamp mature;// 到期时间

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public Timestamp getBegin() {
		return begin;
	}

	public void setBegin(Timestamp begin) {
		this.begin = begin;
	}

	public Timestamp getMature() {
		return mature;
	}

	public void setMature(Timestamp mature) {
		this.mature = mature;
	}
	
	

}
