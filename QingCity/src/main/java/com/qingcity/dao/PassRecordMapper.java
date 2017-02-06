package com.qingcity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qingcity.entity.music.PassRecord;

@Repository
public interface PassRecordMapper {

	/**
	 * 将一条为通关记录设定为已通关
	 * 
	 * @param userId
	 *            玩家id
	 * @param musicId
	 *            通关音乐id
	 * @param difficulty
	 *            通关难度
	 * @return
	 */
	public int updatePassRecordToComplete(@Param("userId") int userId, @Param("musicId") int musicId,
			@Param("difficulty") int difficulty);

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

	/**
	 * 根据玩家id 和city查询该玩家所有难度通关与未通关的歌曲
	 * 
	 * @param userId
	 *            玩家id
	 * @param city
	 *            城市
	 * @return 解锁歌曲列表
	 */
	public List<PassRecord> queryForPassMusic(@Param("userId") int userId, @Param("city") int city);

	/**
	 * 插入一条记录，该首歌曲可能已通关或还未通关
	 * 
	 * @param record
	 * @return 插入数量
	 */
	public int insertSelective(PassRecord record);

}