package com.qingcity.util.test;

import java.util.ArrayList;

import org.junit.Test;

import com.qingcity.entity.UserEntity;
import com.qingcity.utils.FileUtil;
import com.qingcity.utils.GsonUtil;

public class TestJsonAndFileUtil {

	@Test
	public void testJsonToArrayList() {
		String json = FileUtil.ReadFile(FileUtil.getJsonPath() + "userInfo.json");
		ArrayList<UserEntity> list = GsonUtil.jsonToArrayList(json, UserEntity.class);
		for (UserEntity users : list) {
			System.out.println("testJsonToArrayList "  + users.getUsername());
		}
	}

}
