package com.qingcity.service;

import com.qingcity.entity.PlayerEntity;

public interface PlayerService {

	public int getDiamond(int playerId);

	public int getPower(int playerId);

	public int getGold(int playerId);

	public void updateDiamond(int playerId, int diamond);

	public void updatePower(int playerId, int Power);

	public void updateGold(int playerId, int gold);
	
	public void deleteByPlayerId(int playerId);
	
	public void insert(PlayerEntity player);
	
	public void insertSelective(PlayerEntity player);
	
	public PlayerEntity selectByPlayerId(int playerId);
	
	public void updateByPlayerId(int playerId);
	
	public void updateByPlayerIdSelective(PlayerEntity player);
	
	

}
