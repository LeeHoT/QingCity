package com.qingcity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingcity.dao.MusicMapper;
import com.qingcity.entity.MusicEntity;
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
	public int insert(MusicEntity music) {
		return musicMapper.insert(music);
	}

	@Override
	public int insertSelective(MusicEntity music) {
		return musicMapper.insertSelective(music);
	}

	@Override
	public MusicEntity selectByMusicId(Integer musicId) {
		return musicMapper.selectByMusicId(musicId);
	}

	@Override
	public int updateByMusicIdSelective(MusicEntity music) {
		return musicMapper.updateByMusicIdSelective(music);
	}

	@Override
	public int updateByMusicId(MusicEntity music) {
		return musicMapper.updateByMusicId(music);
	}

}
