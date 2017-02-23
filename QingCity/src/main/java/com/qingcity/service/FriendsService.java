package com.qingcity.service;

import java.util.List;

/**
 * 
 * @author leehotin
 * @Date 2017年2月20日 下午5:26:53
 * @Description 好友service接口
 */
public interface FriendsService {

	/**
	 * 查询当前玩家的好友
	 * 
	 * @param userId
	 * @return 好友id列表,当返回大小为0的时候表明该玩家还没有好友
	 */
	List<Integer> selectFriends(Integer userId);

	/**
	 * 查询玩家有的好友请求
	 * 
	 * @param userId
	 * @return 请求添加好友的玩家id列表,当返回大小为0的时候表明该玩家还没有添加好友的请求
	 */
	List<Integer> selectAddRequest(Integer userId);

	/**
	 * 更新好友关系状态,当isFriend参数为true时表明统一加为好友，更新isFriend为true,当isFriend参数为false时，
	 * 直接删除这条记录申请记录
	 * 
	 * @param id
	 *            好友请求id,即该条记录的id
	 * @return
	 */
	int updateStatus(Integer id, Boolean isFriend);

	/**
	 * 检查好友状态，或查询对应玩家是否是自己好友。
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 */
	boolean checkStatus(Integer userId, Integer friendId);

	/**
	 * 删除某位好友
	 * 
	 * @param userId
	 *            玩家id
	 * @param friendId
	 *            好友id
	 * @return 0表示不存在该好友，1表示删除成功
	 */
	int deleteFriend(Integer userId, Integer friendId);

	/**
	 * 添加好友请求
	 * 
	 * @param userId
	 *            玩家自己的id
	 * @param friendId
	 *            玩家id
	 * @return
	 */
	int addRequest(int userId, int friendId);
}
