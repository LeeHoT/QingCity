package com.qingcity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingcity.dao.UserMapper;
import com.qingcity.entity.PlayerEntity;
import com.qingcity.entity.UserEntity;
import com.qingcity.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserEntity userEntity;

	@Override
	public boolean login(UserEntity user) {
		boolean hasLogin = false; // 用户是否已登录成功
		// validate(user);//进行基本验证
		System.out.println("UserService login method's userEntity:" + user);
		userEntity = getUserByNameAndPassword(user.getUsername(), user.getPasswordMd5());
		System.out.println("UserService login method's userEntity:" + user);
		if (userEntity == null) {
			System.out.println("用户名或者密码错误！登录失败");
		} else {
			System.out.println("恭喜您" + userEntity.getUsername() + "成功登录");
			hasLogin = true;
			// 返回登录之后用户需要获取的数据，
		}
		return hasLogin;
	}

	@Override
	public String register(UserEntity user) {
		if (getUserByName(user.getUsername()) != null) {
			System.out.println("用户名存在");
			return "用户名存在";
		} else if (!isExistEmail(user.getEmail())) {
			System.out.println("邮箱已被注册");
			return "邮箱已被注册";
		} else {
			insertUser(user);
			return "注册成功";
		}
	}

	@Override
	public boolean isExistEmail(String email) {
		if (userMapper.isExistEmail(email) != null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isExistPhone(String phone) {
		if (userMapper.isExistPhone(phone) != null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isExistIdCard(String idCard) {
		if (userMapper.isExistIdCard(idCard) != null) {
			return false;
		}
		return true;
	}

	@Override
	public UserEntity getUserByName(String username) {
		return userMapper.getUserByName(username);
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public void insertUser(UserEntity msg) {
		userMapper.insertUser(msg);

	}

	@Override
	public UserEntity getUserByNameAndPassword(String username, String password) {
		return userMapper.getUserByName(username);

	}

	@Override
	public UserEntity getUserById(int userId) {
		return userMapper.selectByUserId(userId);
	}

	@Override
	public void insertIdCard(String idCard) {
		userMapper.insertIdCard(idCard);
	}

	@Override
	public String getIdCard(int userId) {
		return userMapper.getIdCard(userId);
	}

	@Override
	public void insertEmail(String email) {
		userMapper.insertEmail(email);
	}

	@Override
	public String getEmail(int userId) {
		return userMapper.getEmail(userId);
	}

	@Override
	public void insertPhone(String phone) {
		userMapper.insertPhone(phone);

	}

	@Override
	public String getPhone(int userId) {
		return userMapper.getPhone(userId);
	}


	@Override
	public void deleteByUserId(int userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(PlayerEntity user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertSelective(UserEntity user) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserEntity selectByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateByUserId(int userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateByUserIdSelective(UserEntity user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateByUserIdWithBLOBs(UserEntity user) {
		// TODO Auto-generated method stub

	}

}
