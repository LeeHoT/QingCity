package com.qingcity.service;

import com.qingcity.entity.MusicEntity;

public interface MusicService {
	
	public int deleteByMusicId(Integer musicid);

    public int insert(MusicEntity music);

    public int insertSelective(MusicEntity music);

    public MusicEntity selectByMusicId(Integer musicId);

    public int updateByMusicIdSelective(MusicEntity music);

    public int updateByMusicId(MusicEntity music);

}
