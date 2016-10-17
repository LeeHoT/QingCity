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

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public void insertUser(UserEntity msg) {
		userMapper.insertUser(msg);

	}

	@Override
	public UserEntity getUserByName(String username) {
		return userMapper.getUserByName(username);

	}

	@Override
	public UserEntity getUserById(int userId) {
		return userMapper.getUserById(userId);
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
