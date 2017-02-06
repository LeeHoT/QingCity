package com.qingcity.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingcity.dao.SocietysMapper;
import com.qingcity.entity.Societys;
import com.qingcity.service.SocietyService;

/**
 * 公会Service的实现
 * 
 * @author leehotin
 * @Date 2017年2月6日 下午6:17:35
 * @Description 处理 和公会相关的具体操作。
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
		logger.info("新的公会" + societys.getName() + "已经被创建！");
		societyMapper.createSociety(societys);
	}

	@Override
	public void updateSocietyName(Integer societyId, String name) {
		societyMapper.updateSocietyName(societyId, name);
	}

}
