package com.qingcity.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qingcity.entity.music.MusicScoreEntity;
import com.qingcity.entity.vo.RankVO;

/**
 * 1.应添加一个根据时间删除成绩的方法。。比如删除某个时间以前的所有成绩从而来减轻数据量。
 * 我们可以只保留一个赛季的信息。。赛季过后删除成绩信息，仅保存玩家每首音乐最高成绩，或者什么都不保存
 */
@Repository
public interface MusicScoreMapper {

	/**
	 * 查询玩家当前赛季的评分综合，用于主页右侧评分显示
	 * 
	 * @param userId
	 * @param season
	 * @return
	 */
	public List<MusicScoreEntity> selectSumGrade(@Param("userId") int userId, @Param("season") int season);

	/**
	 * 根据玩家id 和音乐id获取该玩家的平均成绩，用于没有人进行pk的时候
	 * 
	 * @param userId
	 *            玩家id
	 * @param musicId
	 *            音乐id
	 * @return 玩家的最高成绩
	 */
	public int selectScoreByMusicIdAnduserId(@Param("userId") int userId, @Param("musicId") int musicId);

	/**
	 * 一定要转换成json后再往出取,用于查看排名
	 * 
	 * @param season
	 * @return
	 */
	public List<Map<String, RankVO>> queryForRankMap(@Param("season") int season, @Param("city") int city);

	/**
	 * 查询玩家某一城下当前赛季的歌曲及成绩列表 ,用于闯关
	 * 
	 * @param userId
	 *            玩家id
	 * @param season
	 *            当前赛季
	 * @param city
	 *            查询的城类型
	 * @return
	 */
	public LinkedList<MusicScoreEntity> queryForMusicList(@Param("userId") int userId, @Param("season") int season,
			@Param("city") int city);

	/**
	 * 可选择新的插入成绩信息， 不推荐使用，一般也不使用。
	 * 
	 * @param musicScore
	 *            成绩对象
	 * @return 新插入数据的id
	 */
	public int insertScoreSelective(MusicScoreEntity musicScore);

	/**
	 * 更新玩家歌曲成绩信息 可选择更新其中任意一项或几项
	 * 
	 * @param musicScore
	 *            成绩对象
	 * @return 更新的行数
	 */
	public int updateByMusicIdAndUserIdSelective(MusicScoreEntity musicScore);

	/**
	 * 更新成绩所有信息。。所有数据均需完整才可以。不推荐使用
	 * 
	 * @param musicScore
	 *            成绩对象
	 * @return 更新行数
	 */
	public int updateByMusicIdAndUserId(MusicScoreEntity musicScore);

	/**
	 * 根据玩家id和音乐id删除玩家本手音乐的所有成绩，，不推荐使用
	 * 
	 * @param musicId
	 *            音乐id
	 * @param userId
	 *            玩家id
	 * @return 删除的行数
	 */
	public int deleteByMusicIdAndUserId(@Param("musicId") int musicId);

	/**
	 * 根据玩家id查询玩家某首音乐的平均成绩，，可在无人pk时处理结果时使用
	 * 
	 * @param musicId
	 *            音乐id
	 * @param userId
	 *            玩家id
	 * @return 查询到的成绩信息
	 */
	public int selectScoreByMusicIdAndUserId(@Param("musicId") int musicId, @Param("userId") int userId);

	/**
	 * 根据音乐id和玩家等级范围查询来查询玩家，用于很少玩家进行PK时使用
	 * 
	 * 条件 between level - 2 and level + 2
	 * 
	 * @param musicId
	 * @return
	 */
	public List<Integer> selectPlayerByMusicIdAndLevel(@Param("musicId") int musicId, @Param("levelOff") int levelOff,
			@Param("levelEnd") int levelEnd);

	/**
	 * 查询 玩家某首音乐的今日当前难度的记录
	 * 
	 * @param userId
	 *            玩家id
	 * @param musicId
	 *            音乐id
	 * @param difficulty
	 *            难度
	 * @param zero
	 *            当天0点 TimeUtil.getStartTime()
	 * @param twelve
	 *            当天12点 TimeUtil.getEndTime()
	 * @return 不存在则返回空，否则返回符合要求的那条记录信息
	 */
	public MusicScoreEntity selectTodayRecord(@Param("userId") int userId, @Param("musicId") int musicId,
			@Param("difficulty") int difficulty, @Param("zero") Date zero, @Param("twelve") Date twelve);

	/**
	 * 更新玩家成绩记录。当一名玩家今日某一首歌的成绩刷新记录时对记录进行更新
	 * 
	 * @param musicScoreEntity
	 *            成绩信息，， 包含day grade score和id
	 * @return 返回更新的行数
	 */
	public int updateRecord(MusicScoreEntity musicScoreEntity);

	public MusicScoreEntity selectByMusicIdAndUserId(@Param("userId") int userId, @Param("musicId") int musicId);

}