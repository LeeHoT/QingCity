package com.qingcity.entity;

import java.io.Serializable;
import java.util.List;

public class Societys implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer societyId;
	private String name;
	private Integer integration;
	private String notice;

	private List<PlayerEntity> pList;// 公会玩家列表

	public List<PlayerEntity> getpList() {
		return pList;
	}

	public void setpList(List<PlayerEntity> pList) {
		this.pList = pList;
	}

	public Integer getSocietyId() {
		return societyId;
	}

	public void setSocietyId(Integer societyId) {
		this.societyId = societyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getIntegration() {
		return integration;
	}

	public void setIntegration(Integer integration) {
		this.integration = integration;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice == null ? null : notice.trim();
	}
}