package com.qingcity.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qingcity.dao.FriendsMapper;
import com.qingcity.entity.Friends;
import com.qingcity.service.FriendsService;

/**
 * 
 * @author leehotin
 * @Date 2017年2月20日 下午5:26:25
 * @Description 好友Service的实现
 */
public class FriendsServiceImpl implements FriendsService {
	private static final Logger logger = LoggerFactory.getLogger(FriendsServiceImpl.class);

	@Autowired
	private FriendsMapper friendMapper;
	@Autowired
	private Friends friends;

	@Override
	public List<Integer> selectFriends(Integer userId) {
		return friendMapper.selectFriends(userId);
	}

	@Override
	public List<Integer> selectAddRequest(Integer userId) {
		return friendMapper.selectAddRequest(userId);
	}

	@Override
	public int updateStatus(Integer id, Boolean isFriend) {
		logger.info("id为" + id + "的好友关系成功建立");
		return friendMapper.updateStatus(id, isFriend);
	}

	@Override
	public boolean checkStatus(Integer userId, Integer friendId) {
		boolean result = false;
		try {
			if (friendMapper.checkStatus(userId, friendId) > 0) {
				result = true;
			}
		} catch (Exception e) {
			// nothing to do
		}
		return result;
	}

	@Override
	public int deleteFriend(Integer userId, Integer friendId) {
		int result = friendMapper.deleteFriend(userId, friendId);
		if (result > 0) {
			logger.info("玩家" + userId + "将好友" + friendId + "删除,解除好友关系");
		}
		return result;
	}

	@Override
	public int addRequest(int userId, int friendId) {
		// 检查是否是好友
		try {
			if (friendMapper.checkStatus(userId, friendId) > 0) {
				// 说明好友关系已存在
				return -1;
			} else {
				// 已经添加过请求
				return 1;
			}
		} catch (Exception e) {
			// 未曾添加过请求
			synchronized (friends) {
				friends.setUser1(userId);
				friends.setUser2(friendId);
				friends.setIsFriend(false);
				friendMapper.addRequest(friends);
				logger.debug("玩家" + userId + "向玩家" + friendId + "添加好友请求");
			}
			return 1;
		}
	}

}
