 package com.qingcity.entity;

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
	private String singerName;// 歌手名
	private String passCondition;//闯关条件

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

	public String getSingerName() {
		return singerName;
	}

	public void setSingerName(String singerName) {
		this.singerName = singerName;
	}

	public String getPassCondition() {
		return passCondition;
	}

	public void setPassCondition(String passCondition) {
		this.passCondition = passCondition;
	}

}
