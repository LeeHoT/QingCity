package com.qingcity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingcity.dao.PlayerMapper;
import com.qingcity.entity.PlayerEntity;
import com.qingcity.service.PlayerService;

@Service("playerService")
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerMapper playerMapper;

	public void setPlayerMapper(PlayerMapper playerMapper) {
		this.playerMapper = playerMapper;
	}

	@Override
	public int getDiamond(int playerId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPower(int playerId) {
		System.out.println(playerMapper);
		return playerMapper.getPower(playerId);
	}

	@Override
	public int getGold(int playerId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateDiamond(int playerId, int diamond) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePower(int playerId, int power) {
		playerMapper.updatePower(playerId, power);
	}

	@Override
	public void updateGold(int playerId, int gold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteByPlayerId(int playerId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(PlayerEntity player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertSelective(PlayerEntity player) {
		// TODO Auto-generated method stub

	}

	@Override
	public PlayerEntity selectByPlayerId(int playerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateByPlayerId(int playerId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateByPlayerIdSelective(PlayerEntity player) {
		// TODO Auto-generated method stub

	}

}
