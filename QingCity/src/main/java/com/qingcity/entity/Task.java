package com.qingcity.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * 任务内容记录
 * @author leehot(李慧婷)
 *
 */
@Repository("task")
@Scope(value="prototype")
public class Task {

	private Integer id;
	private Integer season;// 指明哪个赛季
	private String begin;// 开始时间
	private String end;// 结束时间
	private String joinCondition;// 任务参与条件
	private String condition;// 任务完成条件
	private Integer rewardId;// 指明该任务的奖励id

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSeason() {
		return season;
	}

	public void setSeason(Integer season) {
		this.season = season;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getJoinCondition() {
		return joinCondition;
	}

	public void setJoinCondition(String joinCondition) {
		this.joinCondition = joinCondition;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Integer getRewardId() {
		return rewardId;
	}

	public void setRewardId(Integer rewardId) {
		this.rewardId = rewardId;
	}

}
