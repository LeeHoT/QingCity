package com.qingcity.test.mapper;

import java.util.List;

import org.junit.Test;

import com.qingcity.dao.FriendsMapper;
import com.qingcity.test.init.InitSpringConfigFile;

public class TestFriendMapper {
	FriendsMapper friendMapper = InitSpringConfigFile.getBean(FriendsMapper.class);

	@Test
	public void testUpdateStatus() {
		System.out.println(friendMapper.updateStatus(89, false));
	}

	@Test
	public void testSelectFriends() {
		List<Integer> list = friendMapper.selectAddRequest(87);
		System.out.println(list);
		System.out.println(list.size());
		for (Integer userId : list) {
			System.out.println(userId);
		}
	}

	@Test
	public void testCheckStatus() {
		boolean result = false;
		try {
			int s = friendMapper.checkStatus(34, 65);
			System.out.println(s);
			if (s > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}

	@Test
	public void testDeleteFriends() {
		int result = friendMapper.deleteFriend(39, 38);
		System.out.println(result);
	}

}
