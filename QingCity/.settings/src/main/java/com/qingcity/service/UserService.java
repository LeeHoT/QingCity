package com.qingcity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qingcity.entity.UserEntity;

/**
 * 
 * @author leehot
 * @description: 主要负责用户的登录。注册和退出。
 */
@Service
public interface UserService {
	/**
	 * 查询所有未充过值的玩家
	 * 
	 * @return 玩家列表 仅包含玩家id和username
	 */
	public List<UserEntity> selectNoChargeUser();

	/**
	 * 将当前玩家设置为已经充值过
	 * 
	 * @param userId
	 *            玩家id
	 */
	public void updateUserFirstCharge(int userId);

	/**
	 * 查询到当前玩家是否充过值
	 * 
	 * @param userId
	 *            玩家id
	 * @return 未充过返回false,否则返回true
	 */
	public boolean isCharged(int userId);

	/**
	 * 玩家登录
	 * 
	 * @param user
	 *            玩家信息，， 其中包含用户名及密码
	 * @return true 登录成功 false 登录失败
	 */
	public int login(UserEntity user);

	/**
	 * 玩家注册 ，此时只插入玩家的username passwordMD5以及 regTime
	 * 
	 * @param user
	 *            注册玩家的信息
	 * @return 注册结果信息
	 */
	public String register(UserEntity user);

	/**
	 * 删除玩家 需要先检查玩家是否存在，，若存在则删除 否则删除失败
	 * 
	 * @param userId
	 * @return true 删除成功 false 删除失败
	 */
	public boolean deleteByUserId(int userId);

	/**
	 * 可选择的插入玩家信息 但 username password 以及 regTime 为必填项，，使用时需要注意,推荐使用
	 * 
	 * @param user
	 *            玩家信息对象
	 * @return >1 插入成功 否则插入失败
	 */
	public int insertSelective(UserEntity user);

	/**
	 * 根据玩家id查询玩家信息
	 * 
	 * @param userId
	 *            玩家id
	 * @return 玩家信息 为空则表示玩家不存在
	 */
	public UserEntity selectByUserId(int userId);

	/**
	 * 不可更改注册时间(所有)
	 * 
	 * @param userId
	 */
	public int updateByUserId(UserEntity userEntity);

	/**
	 * 可更改除注册时间外的任意一项或者多项
	 * 
	 * @param user
	 */
	public int updateByUserIdSelective(UserEntity user);

	/**
	 * 插入玩家信息，，用于注册，，不推荐使用
	 * 
	 * @param user
	 * @return
	 */
	public boolean insertUser(UserEntity user);

	/**
	 * 根据用户名密码获取用户信息，用于登录
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return null则用户名或密码错误 否则返回登录玩家的基本信息
	 */
	public UserEntity getUserByNameAndPassword(String username, String password);

	/**
	 * 根据玩家id获取玩家信息
	 * 
	 * @param userId
	 *            玩家id
	 * @return 该玩家信息
	 */
	public UserEntity getUserById(int userId);

	/**
	 * 检验当前邮箱是否已经存在
	 * 
	 * @param email
	 *            需要检验的邮箱
	 * @return true 已存在 false 不存在
	 */
	public boolean isExistEmail(String email);

	/**
	 * 检验当前电话号是否已经存在
	 * 
	 * @param phone
	 *            需要检验的电话号
	 * @return true 已存在 false 不存在
	 */
	public boolean isExistPhone(String phone);

	/**
	 * 检验当前身份证是否已经存在
	 * 
	 * @param idCard
	 *            需要检验的身份证号
	 * @return true 已存在 false 不存在
	 */
	public boolean isExistIdCard(String idCard);

	/**
	 * 插入用户idCard
	 * 
	 * @param idCard
	 *            玩家身份证号码
	 * @param userId
	 *            玩家id
	 * @return true 插入成功 false 插入失败
	 */
	public boolean insertIdCard(String idCard, int userId);

	/**
	 * 获取身份证号
	 * 
	 * @param userId
	 * @return 该玩家身份证号码
	 */
	public String getIdCard(int userId);

	/**
	 * 插入用户email
	 * 
	 * @param email
	 *            玩家正在注册的邮箱
	 * @param userId
	 *            玩家id
	 * @return true 插入成功 false 插入失败
	 */
	public boolean insertEmail(String email, int userId);

	/**
	 * 获取用户EMAIL
	 * 
	 * @param userId
	 *            玩家id
	 * @return 该玩家的邮箱
	 */
	public String getEmail(int userId);

	/**
	 * 插入用户电话号
	 * 
	 * @param phone
	 *            玩家的电话号码
	 * @param userId
	 *            玩家id
	 * @return true 插入成功 false 插入失败
	 */
	public boolean insertPhone(String phone, int userId);

	/**
	 * 获取用户电话号
	 * 
	 * @param userId
	 *            玩家id
	 * @return 该玩家的电话号码
	 */
	public String getPhone(int userId);

	/**
	 * 根据用户名查询相关信息，检测是否存在
	 * 
	 * @param username
	 *            玩家的用户名
	 * @return true 表示该用户名已被占用，false 表示用户名不存在，可用
	 */
	public boolean isExistName(String username);

}
