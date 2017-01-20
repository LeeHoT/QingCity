package com.qingcity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingcity.dao.MusicMapper;
import com.qingcity.entity.music.MusicEntity;
import com.qingcity.service.MusicService;

@Service("musicService")
public class MusicServiceImpl implements MusicService {

	@Autowired
	private MusicMapper musicMapper;

	@Override
	public int deleteByMusicId(Integer musicid) {
		return musicMapper.deleteByMusicId(musicid);
	}

	@Override
	public int insertMusic(MusicEntity music) {
		return musicMapper.insertMusic(music);
	}

	@Override
	public int insertMusicSelective(MusicEntity music) {
		return musicMapper.insertMusicSelective(music);
	}
	@Override
	public int updateByMusicName(MusicEntity music) {
		return musicMapper.updateMusicByMusicName(music);
	}

	@Override
	public int updateByMusicId(MusicEntity music) {
		return musicMapper.updateMusicByMusicId(music);
	}
	
	@Override
	public int selectMusicNum(int city) {
		return musicMapper.selectMusicNum(city);
	}

	@Override
	public int selectMusicIdByPreCondition(int preCondition) {
		return musicMapper.selectMusicIdByPreCondition(preCondition);
	}

	@Override
	public MusicEntity selectByMusicId(Integer musicId) {
		return musicMapper.selectMusicByMusicId(musicId);
	}

	@Override
	public int updatePreCondition(int musicId, int preCondition) {
		return musicMapper.updatePreCondition(musicId, preCondition);
	}


}
