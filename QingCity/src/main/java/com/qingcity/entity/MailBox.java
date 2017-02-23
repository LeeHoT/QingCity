package com.qingcity.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class MailBox implements Serializable {

	private static final long serialVersionUID = 2544285295465016215L;
	private int id;
	private int userId; // 收件玩家id
	private String title; // 邮件标题
	private String content; // 邮件内容
	private Timestamp sendtime;// 发送时间
	private Timestamp overtime;// 过期时间
	private boolean isRead;// 是否已读
	private Integer itemId; // 物品id,目前只有1体力，2金币,且只能有一种
	private Integer itemNum; // 对应物品的数量
	private String sender;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getSendtime() {
		return sendtime;
	}

	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
	}

	public Timestamp getOvertime() {
		return overtime;
	}

	public void setOvertime(Timestamp overtime) {
		this.overtime = overtime;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

}
