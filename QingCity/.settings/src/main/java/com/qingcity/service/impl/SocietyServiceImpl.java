package com.qingcity.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingcity.dao.PlayerMapper;
import com.qingcity.dao.SocietysMapper;
import com.qingcity.entity.Societys;
import com.qingcity.service.SocietyService;

/**
 * 
 * @author leehotin
 *
 */
@Service("societyService")
public class SocietyServiceImpl implements SocietyService {

	private static final Logger logger = LoggerFactory.getLogger(SocietyServiceImpl.class);

	@Autowired
	private SocietysMapper societyMapper;

	@Override
	public boolean checkSocietyName(String name) {
		boolean isExist = true;
		try {
			societyMapper.selectSocietyName(name);
			System.out.println("用户名已被占用");
		} catch (Exception e) {
			e.printStackTrace();
			isExist = false;
			System.out.println("用户名可以使用");
		}
		return isExist;
	}

	@Override
	public Societys selectSocietysMember(int userId, int societyId) {
		return societyMapper.queryForMemberList(userId, societyId);
	}

	@Override
	public void updateSocietyNotice(Integer societyId, String notice) {
		int length = notice.length();
		if (length > 50) {
			System.out.println(" " + societyId + "的新公告过长，更改失败");
			return;
		}
		societyMapper.updateSocietyNotice(societyId, notice);
	}

	@Override
	public void createSociety(Societys societys) {
		societyMapper.createSociety(societys);
	}

	@Override
	public void updateSocietyName(Integer societyId, String name) {
		societyMapper.updateSocietyName(societyId, name);
	}

}
