package com.qingcity.service;

import com.qingcity.entity.PlayerEntity;

public interface PlayerService {
	
	/**
	 * 玩家是否升级。升级则更新玩家等级信息，计算最新经验值，并反馈给客户端
	 * @param playerId
	 * @return
	 */
	public boolean isLevelUp(int playerId);
	
	/**
	 * 获取当前等级升级所需要的总经验值
	 * @param level
	 * @return
	 */
	public int getSumExperience(int level);
	
	public void deleteByPlayerId(int playerId);
	
	public void insert(PlayerEntity player);
	
	public void insertSelective(PlayerEntity player);
	
	public PlayerEntity selectByPlayerId(int playerId);
	
	public void updateByPlayerId(PlayerEntity playerEntity);
	
	/**
	 * 可选择行的更新玩家信息，例如只更新玩家经验值，只更新用户体力等等
	 * @param player
	 */
	public void updateByPlayerIdSelective(PlayerEntity player);
	
	

}
