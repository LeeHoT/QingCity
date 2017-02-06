package com.qingcity.test.mapper;

import org.junit.Test;

import com.qingcity.dao.SocietysMapper;
import com.qingcity.entity.PlayerEntity;
import com.qingcity.entity.Societys;
import com.qingcity.test.init.InitSpringConfigFile;

public class TestSocietyMapper {

	SocietysMapper mapper = InitSpringConfigFile.getBean(SocietysMapper.class);
	public int userId = 39;
	public int city = 1;

	@Test
	public void testQueryForPassMusic() {
		Societys societys = mapper.queryForMemberList(-1, -1);
		if (societys == null) {
			System.out.println("该工会不存在");
			return;
		}
		for (PlayerEntity user : societys.getpList()) {
			System.out.println();
			System.out.println(user.getUserId());
			System.out.println(user.getJob());
		}
	}

	@Test
	public void testUpdateNotice() {
		mapper.updateSocietyNotice(4,
				"我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告我更新了公告");
	}

	@Test
	public void testCreateSociety() {
		Societys societys = new Societys();
		societys.setName("辽宁工大");
		societys.setIntegration(0);
		societys.setNotice(null);
		mapper.createSociety(societys);
		System.out.println(societys.getSocietyId());
	}

	@Test
	public void checkNameIsUsed() {
		boolean isExist = true;
		try {
			mapper.selectSocietyName("辽宁工大");
		} catch (Exception e) {
			e.printStackTrace();
			isExist = false;
			System.out.println("用户名可以使用");
		}
		if (isExist) {
			System.out.println("用户名已被占用");
		}

	}
}
