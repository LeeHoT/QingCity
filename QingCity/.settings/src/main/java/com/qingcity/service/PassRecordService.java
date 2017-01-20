package com.qingcity.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qingcity.entity.music.PassRecord;

public interface PassRecordService {

	/**
	 * 根绝玩家id 和city查询该玩家所有通关与未通关的歌曲
	 * 
	 * @param userId
	 *            玩家id
	 * @param city
	 *            城市
	 * @param difficulty
	 *            难度 1 2 3
	 * @return 解锁歌曲列表
	 */
	public List<PassRecord> queryForPassMusic(int userId, int city, int difficulty);

	/**
	 * 添加一条通关记录，
	 * 
	 * @param passRecord
	 *            通关记录内容，同时记录了相关解锁信息和通关信息
	 * @return 新增记录的行数
	 */
	public int insertPassRecord(PassRecord passRecord);

	/**
	 * 将玩家的该关卡设置为通关，并解锁下一关: 查询目前难度已通关数(此处注意是已通关数而不是解锁数)是否达到解锁下一难度的数量，
	 * 若达到则将下一难度第一首歌去解锁, 否则查询歌曲的preCondition等于当前歌曲musicId的歌曲并将该歌曲设置为未通关(注：
	 * 第一首歌不含解锁条件， 解锁下一难度第一首歌只看已解锁上一难度歌曲的数量), 添加完通关记录后
	 * 
	 * @return -2 表示数据更新成功且玩家已通过所有关卡，1 则表明数据更新成功
	 */
	public int updatePassRecordToComplete(PassRecord passRecord);

	/**
	 * 获取玩家当前城下制定难度解锁歌曲的数量
	 * 
	 * @param userId
	 *            玩家id
	 * @param city
	 *            城代号
	 * @param difficulty
	 *            难度
	 * @return 已经通关的歌曲数量
	 */
	public int selectPassNum(@Param("userId") int userId, @Param("city") int city, @Param("difficulty") int difficulty);

	/**
	 * 查询该玩家所有通关歌曲的数量
	 * 
	 * @param userId
	 *            玩家id
	 * @return 通关总数
	 */
	public int selectCompleteNum(int userId);

}
