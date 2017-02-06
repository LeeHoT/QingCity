package com.qingcity.test.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.qingcity.dao.MailBoxMapper;
import com.qingcity.entity.MailBox;
import com.qingcity.test.init.InitSpringConfigFile;
import com.qingcity.util.TimeUtil;

public class TestMailBoxMapper {

	MailBoxMapper mapper = InitSpringConfigFile.getBean(MailBoxMapper.class);

	@Test
	public void testDeleteMail() {
		int res = 0;
		try {
			res = mapper.deleteMailById(39, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(res);
	}

	@Test
	public void testSelectMail() {
		List<MailBox> list = mapper.selectMail(1);
		System.out.println(list.size());
		MailBox mailbox = mapper.selectMailDetail(2);
		System.out.println(mailbox);
	}

	@Test
	public void testSendMail() {
		MailBox mailbox = new MailBox();
		mailbox.setUserId(39);
		mailbox.setSendtime(TimeUtil.Date2Timestamp(new Date()));
		mailbox.setOvertime(TimeUtil.Date2Timestamp(TimeUtil.addFixedDay(new Date(), 14)));
		mailbox.setRead(false);
		mailbox.setTitle("赠送体力");
		mailbox.setContent("玩家赠送给了你体力");
		mailbox.setItemId(1);
		mailbox.setItemNum(1000);
		mapper.sendMail(mailbox);
		for (MailBox mailboxG : mapper.selectMail(39)) {
			System.out.println(mailboxG.getContent());
			System.out.println(mailboxG.isRead());
			System.out.println(mailboxG.getItemId());
			System.out.println(mailboxG.getSendtime());
			System.out.println(mailboxG.getOvertime());
		}
	}

	@Test
	public void testGetItem() {
		mapper.getItemFromMailBox(7);
		for (MailBox mailboxG : mapper.selectMail(39)) {
			System.out.println(mailboxG.getContent());
			System.out.println(mailboxG.isRead());
			System.out.println(mailboxG.getItemId());
			System.out.println(mailboxG.getItemId());
			System.out.println(mailboxG.getItemNum());
		}
	}
	@Test
	public void testUpateStatus(){
		mapper.updateMailReaded(3);
	}

}
