package com.qingcity.service;

import org.springframework.stereotype.Service;

import com.qingcity.entity.PlayerEntity;
import com.qingcity.entity.UserEntity;

/**
 * 
 * @author leehot
 * @description: 主要负责用户的登录。注册和退出。
 */
@Service
public interface UserService {

    public void deleteByUserId(int userId);
	
	public void insert(PlayerEntity user);
	
	public void insertSelective(UserEntity user);
	
	public UserEntity selectByUserId(int userId);
	
	/**
	 * 可更改任何内容
	 * @param userId
	 */
	public void updateByUserId(int userId);
	
	/**
	 * 不可更改注册时间(所有)
	 * @param user
	 */
	public void updateByUserIdSelective(UserEntity user);
	
	/**
	 * 可更新注册时间(所有)
	 * @param user
	 */
	public void updateByUserIdWithBLOBs(UserEntity user);
	
	public void insertUser(UserEntity msg);

	public UserEntity getUserByName(String username);

	public UserEntity getUserById(int userId);
	
	public boolean isExistEmail(String email);
	
	public boolean isExistPhone(String phone);
	
	public boolean isExistIdCard(String idCard);

	public void insertIdCard(String idCard);

	/**
	 * 获取身份证号
	 * 
	 * @param userId
	 * @return
	 */
	public String getIdCard(int userId);

	/**
	 * 插入用户email
	 * 
	 * @param email
	 */
	public void insertEmail(String email);

	/**
	 * 获取用户EMAIL
	 * 
	 * @param userId
	 * @return
	 */

	public String getEmail(int userId);

	/**
	 * 插入用户电话号
	 * 
	 * @param phone
	 */
	public void insertPhone(String phone);

	/**
	 * 获取用户电话号
	 * 
	 * @param userId
	 * @return
	 */
	public String getPhone(int userId);
	

}
