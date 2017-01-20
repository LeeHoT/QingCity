package com.qingcity.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class MailBox implements Serializable {

	private static final long serialVersionUID = 2544285295465016215L;
	private int id;
	private int playerId; // 收件玩家id
	private String title; // 邮件标题
	private String content; // 邮件内容
	private Timestamp sendtime;// 发送时间
	private Timestamp overtime;// 过期时间
	private String hyperlink;// 超链接内容
	private boolean isRead;// 是否已读

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

	public String getHyperlink() {
		return hyperlink;
	}

	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	@Override
	public String toString() {
		return "Email [id=" + id + ", playerId=" + playerId + ", title=" + title + ", content=" + content
				+ ", sendtime=" + sendtime + ", hyperlink=" + hyperlink + ", isRead=" + isRead + "]";
	}

}
