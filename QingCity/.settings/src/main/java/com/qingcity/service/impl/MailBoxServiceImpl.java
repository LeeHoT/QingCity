package com.qingcity.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingcity.dao.MailBoxMapper;
import com.qingcity.entity.MailBox;
import com.qingcity.service.MailBoxService;

@Service("mailBoxService")
public class MailBoxServiceImpl implements MailBoxService {
	private static final Logger logger = LoggerFactory.getLogger(MailBoxServiceImpl.class);

	@Autowired
	private MailBoxMapper mailBoxMapper;

	@Override
	public int deleteById(Integer id) {
		int result = mailBoxMapper.deleteByPrimaryKey(id);
		if (result > 0) {
			logger.warn("id为{}的邮件被删除", id);
			return 1;
		}
		return -1;
	}

	@Override
	public int insert(MailBox mailbox) {
		int result = mailBoxMapper.insert(mailbox);
		if (result > 0) {
			logger.info("成功为以为用户添加一份邮件");
			return 1;
		}
		return -1;
	}

	@Override
	public int insertSelective(MailBox mailbox) {
		return 0;
	}

	@Override
	public MailBox selectByPlayerId(Integer id) {
		mailBoxMapper.selectByPlayerId(id);
		return null;
	}

	@Override
	public int updateByPlayerIdSelective(MailBox mailbox) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPlayerIdWithBLOBs(MailBox mailbox) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPlayerId(MailBox mailbox) {
		// TODO Auto-generated method stub
		return 0;
	}

}
