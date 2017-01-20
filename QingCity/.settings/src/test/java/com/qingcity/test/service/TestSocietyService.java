package com.qingcity.test.service;

import org.junit.Test;
import com.qingcity.service.SocietyService;
import com.qingcity.test.init.InitSpringConfigFile;

public class TestSocietyService {

	SocietyService societyService = InitSpringConfigFile.getBean(SocietyService.class);

	@Test
	public void testCheckName() {
		boolean res = societyService.checkSocietyName("辽宁工大");
		System.out.println(res);
	}
}
