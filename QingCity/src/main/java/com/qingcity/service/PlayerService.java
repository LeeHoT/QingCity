package com.qingcity.service;

import java.sql.Timestamp;
import java.util.Map;

import com.qingcity.entity.PlayerEntity;

public interface PlayerService {

	/**
	 * 玩家是否升级。升级则更新玩家等级信息，计算最新经验值，并反馈给客户端
	 * 
	 * @param userId
	 *            玩家id
	 * @return 返回新增等级和当前等级剩余经验的键值对，其中键值分别为 exp 和 level
	 */
	Map<String, Integer> LevelUp(int userId, int exp);

	/**
	 * 获取当前等级升级所需要的总经验值
	 * 
	 * @param level
	 * @return
	 */
	int getSumExperience(int level);

	/**
	 * 根据玩家id删除玩家信息，， 不推荐使用
	 * 
	 * @param userId
	 *            玩家id
	 * @return 更新行数 >0则表示删除成功
	 */
	int deleteByUserId(int userId);

	/**
	 * 可选择行的添加玩家信息,只用于刚注册时使用，需要保证玩家信息完整
	 * 
	 * @param player
	 *            玩家信息对象
	 * @return
	 */
	int insertSelective(PlayerEntity player);

	/**
	 * 根据玩家id查询玩家信息
	 * 
	 * @param playerId
	 *            当前玩家id
	 * @return 玩家信息
	 */
	PlayerEntity selectByUserId(int userId);

	/**
	 * 根据昵称查询玩家信息
	 * 
	 * @param nickname
	 * @return
	 */
	PlayerEntity selectByNickname(String nickname);

	/**
	 * 根据玩家等级查询玩家id
	 * 
	 * @param level
	 *            查询等级
	 * @return 玩家id
	 */
	int selectUserByLevel(int level);

	/**
	 * 可选择行的更新玩家信息，例如只更新玩家经验值，只更新用户体力等等
	 * 
	 * @param player
	 *            玩家信息对象
	 * @return 更新行数 >0 则表示更新成功
	 */
	int updateByUserIdSelective(PlayerEntity player);

	/**
	 * 检查玩家上次更新体力的时间
	 * 
	 * @param userId
	 *            玩家id
	 * @return 上次更新体力的时间
	 */
	Timestamp getLastAddPowerTime(int userId);

	/**
	 * 更新玩家体力 从此需要查询上次的电梯里更新时间，从而判断需要自动恢复多少体力，此时体力恢复有上限，超出后不再多增，之后
	 * 再加上客户端传来的数量，一并存入服务器
	 * 
	 * @param power
	 *            新增体力数量
	 * @param userId
	 *            玩家id
	 * 
	 * @return -1 表示体力不足，1更新成功
	 */
	int updatePower(int power, int userId);

	/**
	 * 获取玩家的当前体力值
	 * 
	 * @param userId
	 * @return 玩家体力值
	 */
	int getPower(int userId);

	/**
	 * 更新玩家体力
	 * 
	 * @param gold
	 *            新增金币数量
	 * @param userId
	 *            玩家id
	 */
	int updateGold(int gold, int userId);

	/**
	 * 获取玩家的当前金币值
	 * 
	 * @param userId
	 *            玩家id
	 * @return 玩家金币值
	 */
	int getGold(int userId);

	/**
	 * 更新玩家体力
	 * 
	 * @param diamond
	 *            新增体力数量
	 * @param userId
	 *            玩家id
	 */
	int updateDiamond(int diamond, int userId);

	/**
	 * 获取玩家的当前钻石值
	 * 
	 * @param userId
	 *            玩家id
	 * @return 玩家的钻石数量
	 */
	int getDiamond(int userId);

	/**
	 * 更新玩家的的头像
	 * 
	 * @param icon
	 *            头像名
	 * @param userId
	 *            玩家id
	 */
	void updateIcon(String icon, int userId);

	/**
	 * 获取当前玩家的头像名称
	 * 
	 * @param userId
	 *            玩家id
	 * @return 头像名称
	 */
	String getIcon(int userId);

	/**
	 * 更新玩家昵称
	 * 
	 * @param userId
	 *            玩家id
	 * @param nickname
	 *            更新后的昵称
	 */
	void updateNickname(int userId, String nickname);

	/**
	 * 离开公会
	 * 
	 * @param userId
	 *            准备离开工会的玩家id
	 */
	void quitSociety(int userId);

	/**
	 * 加入公会
	 * 
	 * @param userId
	 *            玩家id
	 * @param societyId
	 *            公会id
	 */
	void joinSociety(int userId, int societyId);

	/**
	 * 更新玩家贡献值
	 * 
	 * @param userId
	 *            玩家id
	 * @param contribution
	 *            新增贡献值
	 */
	void updateContribution(int userId, int contribution);

	/**
	 * 更新玩家在公会中的职位
	 * 
	 * @param userId
	 *            玩家id
	 * @param job
	 *            1 会长，2 副会长，3 成员
	 */
	void updateJob(int userId, int job);

	/**
	 * 查询玩家的公会信息
	 * 
	 * @param userId
	 * @return
	 */
	PlayerEntity getPlayerSocietyInfo(int userId);

	/**
	 * 检查当前玩家是否拥有公会的管理员权限，管理员权限仅会长和副会长
	 * 
	 * @param userId
	 *            玩家id
	 * @return 有权限则返回true 否则返回false
	 */
	boolean hasManagerAuth(int userId);

	/**
	 * 更新玩家最近一次登录时间，时间即为当前时间(上次登录的下线时间)
	 * 
	 * @param userId
	 *            玩家id
	 */
	void updateLoginTime(int userId);

	/**
	 * 更新玩家个性签名
	 * 
	 * @param signature
	 *            个性签名
	 * @param userId
	 *            玩家id
	 */
	void updateSignature(String signature, int userId);

}
