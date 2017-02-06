package com.qingcity.service.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.qingcity.dao.MusicScoreMapper;
import com.qingcity.entity.music.MusicScoreEntity;
import com.qingcity.entity.vo.RankVO;
import com.qingcity.service.MusicScoreService;
import com.qingcity.util.GsonUtil;

@Service("musicScoreService")
public class MusicScoreServiceImpl implements MusicScoreService {

	private static final Logger logger = LoggerFactory.getLogger(MusicScoreServiceImpl.class);

	@Autowired
	private MusicScoreMapper musicScoreMapper;

	@Override
	public int deleteByMusicIdAndUserId(int musicId, int userId) {
		return musicScoreMapper.deleteByMusicIdAndUserId(musicId);
	}

	@Override
	public int insert(MusicScoreEntity musicScore) {
		return musicScoreMapper.insertScoreSelective(musicScore);
	}

	@Override
	public int insertSelective(MusicScoreEntity musicScore) {
		return musicScoreMapper.insertScoreSelective(musicScore);
	}

	@Override
	public int selectScoreByMusicIdAndUserId(int musicId, int userId) {
		return musicScoreMapper.selectScoreByMusicIdAndUserId(musicId, userId);
	}

	@Override
	public int updateByMusicIdAndUserIdSelective(MusicScoreEntity musicScore) {
		return musicScoreMapper.updateByMusicIdAndUserIdSelective(musicScore);
	}

	@Override
	public int updateByMusicIdAndUserId(MusicScoreEntity musicScore) {
		return musicScoreMapper.updateByMusicIdAndUserId(musicScore);
	}

	
	
	
	@Override
	public Map<Integer, RankVO> queryForRankMap(int userId, int number, int season, int city) {
		logger.info("玩家[{}]请求查看[{}]城的排行榜", userId, city);
		List<Map<String, RankVO>> list = musicScoreMapper.queryForRankMap(season, city);
		Map<Integer, RankVO> map = new LinkedHashMap<>();
		Gson gson = new Gson();
		String jsonStr = gson.toJson(list);
		List<RankVO> rankList = GsonUtil.jsonToArrayList(jsonStr, RankVO.class);
		for (RankVO rank : rankList) {
			if (rank.getRank() <= number) {
				map.put(rank.getUserId(), rank);
			}
			if (rank.getUserId() == userId && map.get(userId) == null) {
				map.put(rank.getUserId(), rank);
			}
			if (map.size() == number + 1 || (map.size() == number && map.get(userId) != null)) {
				break;
			}
		}
		return map;
	}

	@Override
	public LinkedList<MusicScoreEntity> queryForMusicList(int userId, int season, int city) {
		return musicScoreMapper.queryForMusicList(userId, season, city);
	}

	@Override
	public List<MusicScoreEntity> selectSumGrade(int userId, int season) {
		return musicScoreMapper.selectSumGrade(userId, season);
	}

	@Override
	public List<Integer> selectPlayerByMusicIdAndLevel(int musicId, int levelOff, int levelEnd) {
		return musicScoreMapper.selectPlayerByMusicIdAndLevel(musicId, levelOff, levelEnd);
	}

	@Override
	public MusicScoreEntity selectByMusicIdAndUserId(int userId, int musicId) {
		return musicScoreMapper.selectByMusicIdAndUserId(userId, musicId);
	}
}
