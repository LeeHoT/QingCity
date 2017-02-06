package com.qingcity.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingcity.dao.PlayerMapper;
import com.qingcity.entity.PlayerEntity;
import com.qingcity.service.PlayerService;
import com.qingcity.util.TimeUtil;

@Service("playerService")
public class PlayerServiceImpl implements PlayerService {

	private static final Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);
	private static final Integer POWER_MILLISECOND = 3 * 60 * 1000;// 自动增加一体力需要3分钟的时间
	private static final Integer POWER_UPPER_LIMIT = 60;// 体力自动恢复的上限

	//private static final Integer CHAIR = 1;// 会长
	//private static final Integer VICE_CHAIR = 2;// 副会长
	private static final Integer MEMBER = 3;// 成员

	@Autowired
	private PlayerMapper playerMapper;
	@Autowired
	private PlayerEntity playerEntity;

	@Override
	public int insertSelective(PlayerEntity player) {
		return playerMapper.insertSelective(player);
	}

	/************************* 已经验证 ******************************/

	@Override
	public int deleteByUserId(int userId) {
		return playerMapper.deleteByUserId(userId);

	}

	@Override
	public PlayerEntity selectByUserId(int userId) {
		playerEntity = playerMapper.selectByUserId(userId);
		return playerEntity;
	}

	@Override
	public int updateByUserIdSelective(PlayerEntity playerEntity) {
		Map<String, Integer> map = new HashMap<>();
		map = LevelUp(playerEntity.getUserId(), playerEntity.getExperience());
		System.out.println("server level " + map.get("level"));
		System.out.println("server exp " + map.get("exp"));
		playerEntity.setLevel(map.get("level"));
		playerEntity.setExperience(map.get("exp"));
		return playerMapper.updateByUserIdSelective(playerEntity);
	}

	@Override
	public synchronized Map<String, Integer> LevelUp(int userId, int exp) {
		Map<String, Integer> map = new HashMap<>();
		boolean flag = true;
		playerEntity = selectByUserId(userId);
		if (playerEntity == null) {
			logger.warn("玩家[{}]不存在", userId);
			return null;
		}
		// 玩家当前等级升级所需总经验
		int levelUpNum = 0;
		int remain = playerEntity.getExperience() + exp;
		do {
			int sumExperience = getSumExperience(playerEntity.getLevel() + levelUpNum);
			// 获取添加经验后剩余的经验
			if (remain > sumExperience) {
				remain = remain - sumExperience;
				// 说明经验至少够升的下一级
				logger.info("id为[{}]的玩家升级了", userId);
				levelUpNum++;
			} else {
				flag = false;
			}
		} while (flag);
		map.put("exp", remain);
		map.put("level", levelUpNum);
		return map;
	}

	@Override
	public int getSumExperience(int level) {
		int sumExperience = 0;
		if (level <= 5) {
			sumExperience = level * 100;
		} else {
			sumExperience = 1000 + level * 100 / 2;
		}
		return sumExperience;
	}

	@Override
	public int selectUserByLevel(int level) {
		return playerMapper.selectUserByLevel(level);
	}

	@Override
	public Timestamp getLastAddPowerTime(int userId) {
		return playerMapper.getLastPowUpdateTime(userId);

	}

	@Override
	public int updatePower(int power, int userId) {
		int old_power = getPower(userId);// 查询当前玩家的原始体力
		if (old_power < POWER_UPPER_LIMIT) {
			// 体力没有满，启动自动恢复体力
			// 查询上次的体力更新时间
			Timestamp last = getLastAddPowerTime(userId);
			Timestamp now = new Timestamp(System.currentTimeMillis());
			int add_power = (int) ((now.getTime() - last.getTime()) / POWER_MILLISECOND);
			System.out.println("系统自动恢复的体力" + add_power);
			if (power + old_power + add_power < 0) {
				// 此时power值为负，消耗体力，且现有体力不足
				logger.warn("玩家[{}]的体力数量不足，无法完成消费", userId);
				return -1;
			}
			if ((old_power + add_power) <= POWER_UPPER_LIMIT) {
				// 系统恢复体力仍小于体力上限，直接将体力更新
				logger.warn("玩家[{}]的体力系统恢复满，", userId);
				playerMapper.updatePower(userId, TimeUtil.Date2Timestamp(new Date()), add_power + power);
			} else {
				// 玩家体力系统已恢复满
				playerMapper.updatePower(userId, TimeUtil.Date2Timestamp(new Date()),
						POWER_UPPER_LIMIT - old_power + power);
			}
		} else {
			// 体力已满，直接增加新增体力，并更新时间
			playerMapper.updatePower(userId, TimeUtil.Date2Timestamp(new Date()), power);
		}
		return 1;
	}

	@Override
	public int getPower(int userId) {
		return playerMapper.getPower(userId);

	}

	@Override
	public int updateGold(int gold, int userId) {
		int old_gold = getGold(userId);// 查询当前玩家的原始体力
		if (gold + old_gold < 0) {
			// 此时金币不足，消耗体力，且现有体力不足
			logger.warn("玩家[{}]的金币数量不足，无法完成消费", userId);
			return -1;
		}
		playerMapper.updateGold(userId, gold);
		return 1;
	}

	@Override
	public int getGold(int userId) {
		return playerMapper.getGold(userId);
	}

	@Override
	public int updateDiamond(int diamond, int userId) {
		int old_diamond = getDiamond(userId);
		if (old_diamond + diamond < 0) {
			logger.warn("玩家[{}]的钻石数量不足，无法完成消费", userId);
			return -1;
		}
		playerMapper.updateDiamond(userId, diamond);
		return 1;
	}

	@Override
	public int getDiamond(int userId) {
		return playerMapper.getDiamond(userId);
	}

	@Override
	public void updateIcon(String icon, int userId) {
		playerMapper.updateIcon(icon, userId);
	}

	@Override
	public String getIcon(int userId) {
		return playerMapper.getIcon(userId);
	}

	@Override
	public void updateNickname(int userId, String nickname) {
		playerMapper.updateNickname(userId, nickname);

	}

	@Override
	public void quitSociety(int userId) {
		playerMapper.quitSociety(userId);
	}

	@Override
	public void joinSociety(int userId, int societyId) {
		playerMapper.joinSociety(userId, societyId);
	}

	@Override
	public void updateContribution(int userId, int contribution) {
		if (contribution < 0) {
			return;
		}
		playerMapper.updateContribution(userId, contribution);

	}

	@Override
	public void updateJob(int userId, int job) {
		playerMapper.updateJob(userId, job);
	}

	@Override
	public PlayerEntity getPlayerSocietyInfo(int userId) {
		return playerMapper.getPlayerSocietyInfo(userId);
	}

	@Override
	public boolean hasManagerAuth(int userId) {
		PlayerEntity player = playerMapper.getPlayerSocietyInfo(userId);
		if (player.getJob() != MEMBER) {
			return true;
		}
		return false;
	}

}
