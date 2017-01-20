package com.qingcity.entity.music;

import java.io.Serializable;

public class PassRecord implements Serializable {
	private static final long serialVersionUID = -3941986826504112194L;
	private int id;
	private int userId;
	private int musicId;
	private int city; // 1.漾青城 2.靛青城 3.墨青城
	private int difficulty;// 1. 简单 2.一般 3.困难
	private boolean isComplete;

	private MusicEntity musicEntity;

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

	public int getMusicId() {
		return musicId;
	}

	public void setMusicId(int musicId) {
		this.musicId = musicId;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public MusicEntity getMusicEntity() {
		return musicEntity;
	}

	public void setMusicEntity(MusicEntity musicEntity) {
		this.musicEntity = musicEntity;
	}

}
