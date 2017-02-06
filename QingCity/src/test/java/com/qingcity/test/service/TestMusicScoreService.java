package com.qingcity.test.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Map.Entry;

import org.junit.Test;

import com.qingcity.dao.MusicScoreMapper;
import com.qingcity.entity.music.MusicScoreEntity;
import com.qingcity.entity.vo.RankVO;
import com.qingcity.service.MusicScoreService;
import com.qingcity.test.init.InitSpringConfigFile;
import com.qingcity.util.TimeUtil;

public class TestMusicScoreService {

	MusicScoreService musicScoreService = InitSpringConfigFile.getBean(MusicScoreService.class);
	MusicScoreMapper musicScoreMapper = InitSpringConfigFile.getBean(MusicScoreMapper.class);

	@Test
	public void testGetRankListAndUserRank() {
		// 测试排名列表
		Map<Integer, RankVO> map = musicScoreService.queryForRankMap(39, 6, 2, 1);
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Entry) it.next();
			Object key = entry.getKey();
			System.out.println(map.get(key).getRank());
			Object value = entry.getValue();
			System.out.println("key" + key + ",value:" + value);
		}
	}

	@Test
	public void testQueryForMusicList() {
		LinkedList<MusicScoreEntity> score = musicScoreService.queryForMusicList(39, 2, 1);
		for (MusicScoreEntity musicScore : score) {
			System.out.println("歌曲名" + musicScore.getMusicEntity().getMusicName());
			System.out.println("歌手名" + musicScore.getMusicEntity().getSingerName());
			System.out.println("成绩" + musicScore.getNormalHighScore());
			System.out.println("评分" + musicScore.getMusicGrade());
		}
	}

	@Test
	public void testSelectSumGrade() {
		List<MusicScoreEntity> list = musicScoreService.selectSumGrade(39, 2);
		for (MusicScoreEntity musicScoreEntity : list) {
			System.out.println("城" + musicScoreEntity.getMusicEntity().getCity());
			System.out.println("总评评分" + musicScoreEntity.getMusicGrade());
		}
	}

	@Test
	public void testSelectScoreByMusicIdAndUserId() {
		int score = musicScoreService.selectScoreByMusicIdAndUserId(4, 39);
		System.out.println("歌曲成绩" + score);
	}

	@Test
	public void testSelectByMusicIdAndUserId() {
		MusicScoreEntity musicScoreEntity = musicScoreMapper.selectByMusicIdAndUserId(39, 2);
		System.out.println(TimeUtil.isToday(musicScoreEntity.getDay()));

	}
}
