package com.qingcity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qingcity.entity.Friends;

/**
 * 
 * @author leehotin
 * @Date 2017年2月17日 上午11:58:11
 * @Description 朋友mapper 接口
 */
@Repository
public interface FriendsMapper {

	/**
	 * 查询当前玩家的好友
	 * 
	 * @param userId
	 * @return 好友id列表
	 */
	List<Integer> selectFriends(Integer userId);

	/**
	 * 查询玩家有的好友请求
	 * 
	 * @param userId
	 * @return 请求添加好友的玩家id列表
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
	int updateStatus(@Param("id") Integer id, @Param("isFriend") Boolean isFriend);

	/**
	 * 检查好友状态，或查询对应玩家是否是自己好友。
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 */
	int checkStatus(@Param("userId") Integer userId, @Param("friendId") Integer friendId);

	/**
	 * 删除某位好友
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 */
	int deleteFriend(@Param("userId") Integer userId, @Param("friendId") Integer friendId);

	/**
	 * 添加好友请求
	 * 
	 * @param friends
	 * @return
	 */
	int addRequest(Friends friends);
}