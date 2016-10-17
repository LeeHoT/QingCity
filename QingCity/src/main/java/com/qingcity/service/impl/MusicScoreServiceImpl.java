package com.qingcity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingcity.dao.MusicScoreMapper;
import com.qingcity.entity.MusicScoreEntity;
import com.qingcity.service.MusicScoreService;

@Service("musicScoreService")
public class MusicScoreServiceImpl implements MusicScoreService{
	
	@Autowired
	private MusicScoreMapper musicScoreMapper;

	@Override
	public int deleteByMusicIdAndUserId(int musicId, int userId) {
		return musicScoreMapper.deleteByMusicIdAndUserId(musicId, userId);
	}

	@Override
	public int insert(MusicScoreEntity musicScore) {
		return musicScoreMapper.insert(musicScore);
	}

	@Override
	public int insertSelective(MusicScoreEntity musicScore) {
		return musicScoreMapper.insertSelective(musicScore);
	}

	@Override
	public MusicScoreEntity selectByMusicIdAndUserId(int musicId, int userId) {
		return musicScoreMapper.selectByMusicIdAndUserId(musicId, userId);
	}

	@Override
	public int updateByMusicIdAndUserIdSelective(MusicScoreEntity musicScore) {
		return musicScoreMapper.updateByMusicIdAndUserIdSelective(musicScore);
	}

	@Override
	public int updateByMusicIdAndUserId(MusicScoreEntity musicScore) {
		return musicScoreMapper.updateByMusicIdAndUserId(musicScore);
	}

}
