package com.qingcity.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingcity.dao.PlayerMapper;
import com.qingcity.dao.UserMapper;
import com.qingcity.entity.PlayerEntity;
import com.qingcity.entity.UserEntity;
import com.qingcity.service.UserService;
import com.qingcity.util.TimeUtil;

@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private static final int INITDIAMOND = 60; // 玩家初始钻石数
	private static final int INITGOLD = 10000; // 玩家初始金币数
	private static final int INITPOWER = 60; // 玩家初始体力

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PlayerMapper playerMapper;
	@Autowired
	private UserEntity userEntity;
	@Autowired
	private PlayerEntity playerEntity;

	@Override
	public synchronized int login(UserEntity user) {
		int userId = -1; // 用户是否已登录成功
		// validate(user);//进行基本验证
		System.out.println("UserService login method's userEntity:" + user);
		userEntity = getUserByNameAndPassword(user.getUsername(), user.getPasswordMd5());
		System.out.println("UserService login method's userEntity:" + user);
		if (userEntity == null) {
			System.out.println("用户名或者密码错误！登录失败");
		} else {
			System.out.println("恭喜您" + userEntity.getUsername() + "成功登录");
			userId = userEntity.getUserId();
		}
		return userId;
	}

	@Override
	public synchronized String register(UserEntity user) {
		if (isExistName(user.getUsername())) {
			System.out.println("用户名存在");
			return "用户名存在";
		} else if (isExistEmail(user.getEmail())) {
			System.out.println("邮箱已被注册");
			return "邮箱已被注册";
		} else {
			// 可以注册。。先添加个人信息，后添加玩家基本信息进入玩家表player
			int userId = insertSelective(user);
			if (userId > 0) {
				// 查询用户当前id
				// 设置用户初始信息
				logger.info("玩家[{}]的基本信息填写完成,玩家id为[{}]", user.getUsername(), user.getUserId());
				playerEntity.setUserId(user.getUserId());
				playerEntity.setExperience(0);
				playerEntity.setLevel(1);
				playerEntity.setDiamond(INITDIAMOND);
				playerEntity.setGold(INITGOLD);
				playerEntity.setPower(INITPOWER);
				playerEntity.setLastPowUpdateTime(TimeUtil.Date2Timestamp(new Date()));
				playerEntity.setIcon("icon");
				playerEntity.setSocietyId(-1);
				playerEntity.setJob(0);
				playerEntity.setContribution(0);
				playerEntity.setNickname(user.getUsername());
				playerMapper.insertSelective(playerEntity);
				return "注册成功";
			}
			return "注册失败";
		}
	}

	@Override
	public boolean isExistEmail(String email) {
		if (userMapper.isExistEmail(email) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isExistPhone(String phone) {
		if (userMapper.isExistPhone(phone) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isExistIdCard(String idCard) {
		if (userMapper.isExistIdCard(idCard) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isExistName(String username) {
		if (userMapper.isExistUsername(username) != null) {
			return true;
		}
		return false;
	}

	@Override
	public synchronized boolean insertUser(UserEntity msg) {
		if (userMapper.insertUser(msg) > 0) {
			return true;
		}
		return false;

	}

	@Override
	public UserEntity getUserByNameAndPassword(String username, String password) {
		return userMapper.selectUserByName(username);

	}

	@Override
	public UserEntity getUserById(int userId) {
		return userMapper.selectByUserId(userId);
	}

	@Override
	public synchronized boolean insertIdCard(String idCard, int userId) {
		if (userMapper.insertIdCard(idCard, userId) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public String getIdCard(int userId) {
		return userMapper.getIdCard(userId);
	}

	@Override
	public synchronized boolean insertEmail(String email, int userId) {
		if (userMapper.insertEmail(email, userId) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public String getEmail(int userId) {
		return userMapper.getEmail(userId);
	}

	@Override
	public synchronized boolean insertPhone(String phone, int userId) {
		if (userMapper.insertPhone(phone, userId) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public String getPhone(int userId) {
		return userMapper.getPhone(userId);
	}

	@Override
	public boolean deleteByUserId(int userId) {
		// 检查是否有该id
		if (userMapper.selectByUserId(userId) != null) {
			// 说明玩家存在
			userMapper.deleteByUserId(userId);
			return true;
		}
		return false;
	}

	@Override
	public synchronized int insertSelective(UserEntity user) {
		return userMapper.insertUserSelective(user);
	}

	@Override
	public UserEntity selectByUserId(int userId) {
		return userMapper.selectByUserId(userId);
	}

	@Override
	public int updateByUserId(UserEntity userEntity) {
		return userMapper.updateByUserId(userEntity);
	}

	@Override
	public int updateByUserIdSelective(UserEntity user) {
		return updateByUserIdSelective(user);
	}

	@Override
	public void updateUserFirstCharge(int userId) {
		userMapper.updateChargedByUserId(userId);

	}

	@Override
	public boolean isCharged(int userId) {
		return userMapper.isCharged(userId);
	}

	@Override
	public List<UserEntity> selectNoChargeUser() {
		return userMapper.selectNoChargeUser();
	}
}
