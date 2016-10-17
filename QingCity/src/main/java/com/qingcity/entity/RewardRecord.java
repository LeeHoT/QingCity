package com.qingcity.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * 玩家任务完成情况记录
 * 
 * @author 李慧婷
 *
 */

@Repository(value="rewardRecord")
@Scope(value="prototype")
public class RewardRecord implements Serializable {
	private static final long serialVersionUID = 5087719700833635376L;
	private int id;
	private int userId; // 玩家id
	private int rewardId; // 任务id
	private boolean isComplete;// 是否完成

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRewardId() {
		return rewardId;
	}

	public void setRewardId(int rewardId) {
		this.rewardId = rewardId;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

}
