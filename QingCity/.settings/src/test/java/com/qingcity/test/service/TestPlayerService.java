package com.qingcity.test.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.IntegerTypeHandler;
import org.junit.Test;

import com.qingcity.entity.PlayerEntity;
import com.qingcity.entity.UserEntity;
import com.qingcity.service.PlayerService;
import com.qingcity.service.UserService;
import com.qingcity.test.init.InitSpringConfigFile;
import com.qingcity.utils.TimeUtil;

/**
 * 测试playerService各项功能是否可用
 * 
 * @author Administrator
 *
 */
public class TestPlayerService {

	private static int USER_ID = 88;
	private static int LEVEL = 1;

	PlayerService playerService = InitSpringConfigFile.getBean(PlayerService.class);
	UserService userService = InitSpringConfigFile.getBean(UserService.class);
	private static List<Integer> listId = new ArrayList<>();

	static {
		for (int i = 90; i < 100; i++) {
			listId.add(i);
		}
	}

	@Test
	public void getPowerOrGoldOrDiamond() {
		// 获取钻石的单独的方法
		int diamond = playerService.getDiamond(USER_ID);
		// 获取体力的单独的方法
		int power = playerService.getPower(USER_ID);
		// 获取金币的单独的方法
		int gold = playerService.getGold(USER_ID);
		System.out.println("共有钻石 " + diamond + " 颗,体力 " + power + " 个以及金币 " + gold);
		// 使用可选择性的对象
		PlayerEntity playerEntity = playerService.selectByUserId(USER_ID);
		System.out.println("共有钻石 " + playerEntity.getDiamond() + " 颗,体力 " + playerEntity.getDiamond() + " 个以及金币 "
				+ playerEntity.getGold());
	}

	@SuppressWarnings("unused")
	@Test
	public void updatePlayerInfo() {
		// 获取玩家初始信息
		PlayerEntity playerEntity = playerService.selectByUserId(USER_ID);
		System.out.println("玩家id" + playerEntity.getUserId());
		System.out.println("玩家等级" + playerEntity.getLevel());
		System.out.println("玩家当前经验" + playerEntity.getExperience());
		System.out.println("玩家体力上次更新时间" + playerEntity.getLastPowUpdateTime());
		// 更新玩家头像
		playerService.updateIcon("newIcon", USER_ID);
		System.out.println("玩家的新头像为: " + playerService.getIcon(USER_ID));
		// 更新玩家体力,玩家新增250的体力
		System.out.println("更新体力钱玩家的体力更新时间" + playerService.getLastAddPowerTime(USER_ID));
		System.out.println("更新前的玩家体力" + playerService.getPower(USER_ID));
		playerService.updatePower(250, USER_ID);
		System.out.println("更新后的玩家体力" + playerService.getPower(USER_ID));
		System.out.println("更新体力后玩家的体力更新时间" + playerService.getLastAddPowerTime(USER_ID));
		playerService.updateDiamond(45, USER_ID);
		System.out.println("更新后的玩家钻石" + playerService.getDiamond(USER_ID));
		playerService.updateGold(1000, USER_ID);
		System.out.println("更新后的玩家金币" + playerService.getGold(USER_ID));

		for (Integer userId : listId) {

			playerEntity.setUserId(userId);
			playerEntity.setIcon("newIcon2");
			playerEntity.setPower(50);
			playerEntity.setDiamond(50);
			playerEntity.setGold(50);
			playerEntity.setLastPowUpdateTime(TimeUtil.Date2Timestamp(new Date()));
			playerEntity.setExperience(5 * userId);
			int result = playerService.updateByUserIdSelective(playerEntity);
		}

	}

	@Test
	public void testDeletePlayer() {
		int result = playerService.deleteByUserId(89);
		if (result > 0) {
			System.out.println("成功删除玩家");
		} else {
			System.out.println("删除玩家失败");
		}

	}

	@Test
	public void testInsertPlayer() {

		PlayerEntity playerEntity = new PlayerEntity();
		// ***利用playerEntity对象动态更新
		for (int i = 0; i < 10; i++) {

			UserEntity user = new UserEntity();
			user.setCharged(false);
			user.setUsername("插入玩家测试" + i + i);
			user.setPasswordMd5("lalala");
			user.setEmail("1354564" + i + i+ "14@qq.com");
			user.setRegTime(TimeUtil.Date2Timestamp(new Date()));
			String result = userService.register(user);

			System.out.println("注册结果为" + result);
		}
	}
	
	@Test
	public void testUpdatePower(){
		Timestamp last = playerService.getLastAddPowerTime(39);
		Timestamp t = new Timestamp(System.currentTimeMillis());
		System.out.println(last);
		System.out.println(t);
		System.out.println((t.getTime()-last.getTime())/1000);
	}

}
