package com.qingcity.entity.music;

import java.io.Serializable;

/**
 * 
 * @author 李慧婷
 * 
 */

public class MusicEntity implements Serializable {

	private static final long serialVersionUID = -6061356776254342985L;
	private Integer musicId; // 音乐id
	private String musicName;// 音乐名
	private Integer city;// 1,漾青城 2.靛青城 3.墨青城
	private String singerName;// 歌手名
	private String preCondition;// 前置条件
	

	public Integer getMusicId() {
		return musicId;
	}

	public void setMusicId(Integer musicId) {
		this.musicId = musicId;
	}

	public String getMusicName() {
		return musicName;
	}

	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getSingerName() {
		return singerName;
	}

	public void setSingerName(String singerName) {
		this.singerName = singerName;
	}

	public String getPassCondition() {
		return preCondition;
	}

	public void setPassCondition(String passCondition) {
		this.preCondition = passCondition;
	}

	public String getPreCondition() {
		return preCondition;
	}

	public void setPreCondition(String preCondition) {
		this.preCondition = preCondition;
	}

}
