package com.qingcity.dao;

import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Repository;

import com.qingcity.entity.PlayerEntity;


@Repository("playerMapper")
public interface PlayerMapper {

	int deleteByPlayerId(Integer playerid);

	int insert(PlayerEntity record);

	int insertSelective(PlayerEntity record);

	PlayerEntity selectByPlayerId(Integer playerid);

	int updateByPlayerIdSelective(PlayerEntity player);

	int updateByPlayerId(PlayerEntity player);

	/**
	 * 更新用户体力
	 * 
	 * @param playerId
	 */
	public void updatePower(@Param("playerId") int playerId, @Param("power") int power);

	/**
	 * 获取当前体力值
	 * 
	 * @param playerId
	 * @return
	 */
	public int getPower(int playerId);

	/**
	 * 获取用户金币数
	 * 
	 * @param playerId
	 * @return
	 */
	public int getGold(int playerId);

	/**
	 * 更新用户金币数
	 * 
	 * @param playerId
	 */
	public void updateGold(@Param("playerId") int playerId, @Param("gold") int gold);

	/**
	 * 获取用户钻石数
	 * 
	 * @param playerId
	 * @return
	 */
	public int getDiamond(int playerId);

	/**
	 * 更新用户金币数
	 * 
	 * @param playerId
	 */
	public void updateDiamond(@Param("playerId") int playerId, @Param("diamond") int diamond);

}
