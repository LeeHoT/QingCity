package com.qingcity.entity.task;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * 保存用户每件任务完成情况，以及每件任务奖励领取状况
 * 
 * 每位玩家的每件任务的具体状态。
 * 
 * @author Administrator
 *
 */

@Repository(value="taskStatus")
@Scope(value="prototype")
public class TaskStatus {

	private Integer id;
	private Integer userId; //玩家id
	private Integer taskId; //任务id
	private boolean status;// 任务状态
	private boolean rewardStatus;// 是否领取奖励

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isRewardStatus() {
		return rewardStatus;
	}

	public void setRewardStatus(boolean rewardStatus) {
		this.rewardStatus = rewardStatus;
	}

}
