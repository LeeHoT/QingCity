package com.qingcity.entity.music;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 李慧婷
 * 
 *         description:每首音乐所包含的内容 其中musicGrade和NormalHighScore不存放数据，
 */
public class MusicScoreEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer musicId; // 音乐id
	private Integer userId; // 玩家id或用户id

	private int season; // 赛季
	private int week; // 周
	private Timestamp day; // 天

	private Integer musicGrade; // 歌曲分数评级
	private Integer NormalHighScore; // 最高分数
	private Integer difficulty;// 难度

	private MusicEntity musicEntity;

	public Integer getMusicId() {
		return musicId;
	}

	public void setMusicId(Integer musicId) {
		this.musicId = musicId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public Timestamp getDay() {
		return day;
	}

	public void setDay(Timestamp day) {
		this.day = day;
	}

	public Integer getMusicGrade() {
		return musicGrade;
	}

	public void setMusicGrade(Integer musicGrade) {
		this.musicGrade = musicGrade;
	}

	public Integer getNormalHighScore() {
		return NormalHighScore;
	}

	public void setNormalHighScore(Integer normalHighScore) {
		NormalHighScore = normalHighScore;
	}

	public MusicEntity getMusicEntity() {
		return musicEntity;
	}

	public void setMusicEntity(MusicEntity musicEntity) {
		this.musicEntity = musicEntity;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

}
