package com.qingcity.service;

import com.qingcity.entity.MusicScoreEntity;

public interface MusicScoreService {
	public int deleteByMusicIdAndUserId(int musicId,int userId);

    public int insert(MusicScoreEntity musicScore);

    public int insertSelective(MusicScoreEntity musicScore);

    public MusicScoreEntity selectByMusicIdAndUserId(int musicId,int userId);

    public int updateByMusicIdAndUserIdSelective(MusicScoreEntity musicScore);

    public int updateByMusicIdAndUserId(MusicScoreEntity musicScore);

}
