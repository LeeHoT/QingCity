package com.qingcity.test.mapper;

import org.junit.Test;

import com.qingcity.dao.PlayerMapper;
import com.qingcity.entity.PlayerEntity;
import com.qingcity.test.init.InitSpringConfigFile;

public class TestPlayerMapper {

	PlayerMapper mapper = InitSpringConfigFile.getBean(PlayerMapper.class);
	public int userId = 39;
	public int city = 1;

	@Test
	public void testJoinSociety() {
		mapper.joinSociety(68, 1);
	}

	@Test
	public void testQuitSociety() {
		mapper.quitSociety(68);
	}
	
	@Test
	public void testUpdateContribution(){
		mapper.updateContribution(68, 2000);
	}
	
	@Test
	public void testUpdateJob(){
		mapper.updateJob(68, 1);
	}
	
	@Test
	public void testGetPlayerInfo(){
		PlayerEntity player = mapper.selectByUserId(68);
		System.out.println(player.getContribution());
	}

}
