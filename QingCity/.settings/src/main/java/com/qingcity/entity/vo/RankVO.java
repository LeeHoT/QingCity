package com.qingcity.entity.vo;

public class RankVO {
	private int userId;
	private int rank;
	private int a_ms;// 所有成绩总平均分

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getA_ms() {
		return a_ms;
	}

	public void setA_ms(int a_ms) {
		this.a_ms = a_ms;
	}

}
