package com.qingcity.dao;

import org.springframework.stereotype.Repository;

import com.qingcity.entity.UserEntity;

@Repository
public interface UserMapper {

	public int deleteByUserId(Integer userid);

	public int insert(UserEntity user);

	public int insertSelective(UserEntity user);

	/**
	 * 根据用户ID获取用户所有信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserEntity selectByUserId(Integer userid);
	
	public UserEntity selectSelective(UserEntity user);

	/**
	 * 不可修改注册时间
	 * 
	 * @param user
	 * @return
	 */
	public int updateByUserIdSelective(UserEntity user);

	/**
	 * 可修改注册时间
	 * 
	 * @param user
	 * @return
	 */
	int updateByUserIdWithBLOBs(UserEntity user);

	/**
	 * 根据email获取用户信息
	 * 
	 * @param name
	 * @return
	 */

	public UserEntity getUserByName(String name);

	/**
	 * 添加用户信息
	 * 
	 * @param userEntity
	 */
	public void insertUser(UserEntity userEntity);

	/**
	 * 判断是否存在Email
	 * 
	 * @param email
	 * @return
	 */
	public String isExistEmail(String email);

	/**
	 * 判断是否存在phone
	 * 
	 * @param phone
	 * @return
	 */

	public String isExistPhone(String phone);

	/**
	 * 判断是否存在idCard
	 * 
	 * @param idCard
	 * @return
	 */
	public String isExistIdCard(String idCard);

	/**
	 * 插入用户身份证号
	 * 
	 * @param idCard
	 */
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
