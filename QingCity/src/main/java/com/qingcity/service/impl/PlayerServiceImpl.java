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

	@Autowired
	private PlayerEntity playerEntity;

	public void setPlayerMapper(PlayerMapper playerMapper) {
		this.playerMapper = playerMapper;
	}

	@Override
	public void deleteByPlayerId(int playerId) {
		playerMapper.deleteByPlayerId(playerId);

	}

	@Override
	public void insert(PlayerEntity player) {
		playerMapper.insert(player);

	}

	@Override
	public void insertSelective(PlayerEntity player) {
		playerMapper.insertSelective(player);

	}

	@Override
	public PlayerEntity selectByPlayerId(int playerId) {
		playerEntity = playerMapper.selectByPlayerId(playerId);
		return playerEntity;
	}

	@Override
	public void updateByPlayerId(PlayerEntity player) {
		playerMapper.updateByPlayerId(player);

	}

	@Override
	public void updateByPlayerIdSelective(PlayerEntity playerEntity) {
		playerMapper.updateByPlayerIdSelective(playerEntity);

	}

	@Override
	public boolean isLevelUp(int playerId) {
		playerEntity = selectByPlayerId(playerId);
		int sumExperience = getSumExperience(playerEntity.getLevel());
		if (playerEntity.getExperience() >= sumExperience) {
			// 够升级了。。那就直接升了吧。
			// 重新设置当前playEntity中存储的当前用户的信息，设定需要更新的内容。
			playerEntity.setLevel(playerEntity.getLevel() + 1);
			playerEntity.setExperience(playerEntity.getExperience() - sumExperience);
			updateByPlayerIdSelective(playerEntity);
			System.out.println("恭喜玩家 "+playerId+"成功升级");
			return true;
		} else {
			System.out.println("经验还不足以升级。。继续努力吧！");
		}
		return false;
	}

	@Override
	public int getSumExperience(int level) {
		int sumExperience = 0;
		if (level <= 10) {
			sumExperience = level * 100;
		} else {
			sumExperience = 1300 + level * 100 / 2;
		}
		return sumExperience;
	}

}
