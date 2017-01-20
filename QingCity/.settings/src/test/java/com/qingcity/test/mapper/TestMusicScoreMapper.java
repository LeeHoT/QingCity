package com.qingcity.test.mapper;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.qingcity.dao.MusicScoreMapper;
import com.qingcity.entity.music.MusicScoreEntity;
import com.qingcity.entity.vo.RankVO;
import com.qingcity.test.init.InitSpringConfigFile;
import com.qingcity.utils.GsonUtil;
import com.qingcity.utils.TimeUtil;

public class TestMusicScoreMapper {

	MusicScoreMapper musicScoreMapper = InitSpringConfigFile.getBean(MusicScoreMapper.class);

	@Test
	public void testQueryForListMap() {
		LinkedList<MusicScoreEntity> list = musicScoreMapper.queryForMusicList(39, 2, 1);
		for (MusicScoreEntity musicScore : list) {
			System.out.println("音乐名称" + musicScore.getMusicEntity().getMusicName());
			System.out.println("歌手" + musicScore.getMusicEntity().getSingerName());
			System.out.println("最高评分" + musicScore.getMusicGrade());
			System.out.println("最高成绩" + musicScore.getNormalHighScore());
		}
	}

	@Test
	public void testQueryForRankList() {
		List<Map<String, RankVO>> list = musicScoreMapper.queryForRankMap(2, -1);
		Gson gson = new Gson();
		String json = gson.toJson(list);
		List<RankVO> listRank = GsonUtil.jsonToArrayList(json, RankVO.class);
		for (RankVO rank : listRank) {
			System.out.println("用户id" + rank.getUserId());
			System.out.println("排名" + rank.getRank());
			System.out.println("用户成绩" + rank.getA_ms());
		}
		System.out.println(json);
	}

	@Test
	public void testSelectTodayRecord() {
		MusicScoreEntity musicScoreEntity = musicScoreMapper.selectTodayRecord(39, 1, 1, TimeUtil.getStartTime(),
				TimeUtil.getEndTime());
		if (musicScoreEntity != null) {
			System.out.println(musicScoreEntity.getId());
		} else {
			System.out.println(musicScoreEntity);
			System.out.println("没有符合要求的成绩信息");
		}

	}

	@Test
	public void testInsertScoreSelective() {
		Date date = new Date();
		MusicScoreEntity musicScoreEntity = new MusicScoreEntity();
		musicScoreEntity.setMusicId(1);
		musicScoreEntity.setUserId(39);
		musicScoreEntity.setDifficulty(1);
		musicScoreEntity.setSeason(TimeUtil.getSeason(date));
		musicScoreEntity.setWeek(TimeUtil.getWeek(date));
		musicScoreEntity.setDay(TimeUtil.Date2Timestamp(date));
		musicScoreEntity.setMusicGrade(4);
		musicScoreEntity.setNormalHighScore(54664);
		int result = musicScoreMapper.insertScoreSelective(musicScoreEntity);
		System.out.println(result);
		System.out.println(musicScoreEntity.getId());
	}

	@Test
	public void testUpdateRecord() {
		MusicScoreEntity musicScoreEntity = musicScoreMapper.selectTodayRecord(39, 1, 1, TimeUtil.getStartTime(),
				TimeUtil.getEndTime());
		System.out.println(musicScoreEntity);
		musicScoreEntity.setDay(TimeUtil.Date2Timestamp(new Date()));
		if (musicScoreEntity.getNormalHighScore() < 65465) {
			musicScoreEntity.setNormalHighScore(789876);
		} else {
			musicScoreEntity.setNormalHighScore(null);
		}
		if (musicScoreEntity.getMusicGrade() < 6) {
			musicScoreEntity.setMusicGrade(6);
		} else {
			musicScoreEntity.setMusicGrade(null);
		}
		musicScoreEntity.setDay(TimeUtil.Date2Timestamp(new Date()));
		musicScoreMapper.updateRecord(musicScoreEntity);

	}

}
