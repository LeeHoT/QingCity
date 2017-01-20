package com.qingcity.test.mapper;

import java.util.List;

import javax.naming.Context;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.qingcity.dao.MusicMapper;
import com.qingcity.dao.MusicScoreMapper;
import com.qingcity.dao.PassRecordMapper;
import com.qingcity.entity.music.MusicEntity;
import com.qingcity.entity.music.PassRecord;
import com.qingcity.test.init.InitSpringConfigFile;

public class TestMusicMapper {
	
	PassRecordMapper mapper = InitSpringConfigFile.getBean(PassRecordMapper.class);
	public int userId = 39;
	public int city = 1; 
	
	@Test
	public void testQueryForPassMusic(){
		List<PassRecord> list  = mapper.queryForPassMusic(userId, city);
		PassRecord pass = null;
		System.out.println(list);
//		for (MusicEntity music : list) {
//			System.out.println(music.getMusicId());
//			pass = music.getPassList();
//			System.out.println(music.getPassList());
//		}
	}

}
