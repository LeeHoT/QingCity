package com.qingcity.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qingcity.entity.music.MusicScoreEntity;
import com.qingcity.entity.vo.RankVO;

public interface MusicScoreService {

	/**
	 * 查询玩家当前赛季的评分综合，用于主页右侧评分显示
	 * 
	 * @param userId
	 * @param season
	 * @return
	 */
	List<MusicScoreEntity> selectSumGrade(int userId, int season);

	/**
	 * 一定要转换成json后再往出取，，出来排行榜界面后需要修改
	 * 
	 * @param userId
	 *            玩家id
	 * @param number
	 *            需要查询的排名数量
	 * @param season
	 *            赛季
	 * @param city
	 *            小于0表示不考虑城市
	 * @return 大小为number+1或者number的map,包含前number个玩家的排名信息
	 */
	Map<Integer, RankVO> queryForRankMap(int userId, int number, int season, int city);

	/**
	 * 查询玩家某一城下当前赛季的歌曲及成绩列表
	 * 
	 * @param userId
	 *            玩家id
	 * @param season
	 *            当前赛季
	 * @param city
	 *            查询的城类型
	 * @return
	 */
	LinkedList<MusicScoreEntity> queryForMusicList(int userId, int season, int city);

	int deleteByMusicIdAndUserId(int musicId, int userId);

	int insert(MusicScoreEntity musicScore);

	int insertSelective(MusicScoreEntity musicScore);

	/**
	 * 
	 * @param userId
	 * @param musicId
	 * @return
	 */
	MusicScoreEntity selectByMusicIdAndUserId(int userId, int musicId);

	/**
	 * 根据玩家id查询玩家某首音乐的平均成绩，，可在无人pk时处理结果时使用
	 * 
	 * @param musicId
	 *            音乐id
	 * @param userId
	 *            玩家id
	 * @return 查询到的成绩信息
	 */
	int selectScoreByMusicIdAndUserId(int musicId, int userId);

	/**
	 * 根据音乐id和玩家等级范围查询来查询玩家，用于很少玩家进行PK时使用
	 * 
	 * 条件 between level - 2 and level + 2
	 * 
	 * @param musicId
	 *            音乐id
	 * @param levelOff
	 *            等级下限
	 * @param levelEnd
	 *            等级上限
	 * @return 符合要求的玩家列表
	 */
	List<Integer> selectPlayerByMusicIdAndLevel(int musicId, int levelOff, int levelEnd);

	int updateByMusicIdAndUserIdSelective(MusicScoreEntity musicScore);

	int updateByMusicIdAndUserId(MusicScoreEntity musicScore);

}
