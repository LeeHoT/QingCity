package com.qingcity.service.impl;

import java.util.List;

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
	public int deleteById(Integer id, int userId) {
		int result = 0;
		try {
			result = mailBoxMapper.deleteMailById(id, userId);
		} catch (Exception e) {
			result = -1;
			logger.error("删除邮件[{}]发生异常[{}]", id, e);
		}
		if (result > 0) {
			logger.warn("id为[{}]的邮件被删除", id);
		}
		return result;
	}

	@Override
	public int sendMail(MailBox mailBox) {
		int result = 0;
		try {
			result = mailBoxMapper.sendMail(mailBox);
		} catch (Exception e) {
			result = -1;
			logger.error("发送邮件时间,发送时出现异常[{}]", e);
		}
		if (result > 0) {
			logger.debug("成功为一位用户添加一份邮件");

		}
		return result;
	}

	@Override
	public MailBox selectMailDetail(int id) {
		return mailBoxMapper.selectMailDetail(id);
	}

	@Override
	public List<MailBox> selectMail(Integer userId) {
		return mailBoxMapper.selectMail(userId);
	}

	@Override
	public void getItemFromMailBox(int mailId) {
		logger.debug("id为[{}]的邮件中的奖励被领取", mailId);
		mailBoxMapper.getItemFromMailBox(mailId);
	}

	@Override
	public void updateMailReaded(int id) {
		logger.debug("id为[{}]的邮件已被玩家查看", id);
		mailBoxMapper.updateMailReaded(id);
	}
}
