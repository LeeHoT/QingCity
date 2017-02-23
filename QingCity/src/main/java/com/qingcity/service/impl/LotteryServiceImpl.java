package com.qingcity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingcity.dao.GiftMapper;
import com.qingcity.dao.GiftRecordMapper;
import com.qingcity.dao.PlayerMapper;
import com.qingcity.entity.GiftRecord;
import com.qingcity.service.LotteryService;

@Service
public class LotteryServiceImpl implements LotteryService {

	private static final Integer LOTTERY_LEVE_LIMIT = 15;// 抽奖等级限制为15+

	@Autowired
	private GiftMapper giftMapper;
	@Autowired
	private GiftRecordMapper giftRecordMapper;
	@Autowired
	private PlayerMapper playerMapper;

	@Override
	public int checkTicketCount(int id) {
		return giftMapper.selectGiftCount(id);
	}

	@Override
	public void updateTicketCount(int id, int count) {
		giftMapper.updatGiftCount(id, count);

	}

	@Override
	public List<GiftRecord> selectWinnerInfo(int type) {
		return giftRecordMapper.selectWinByType(type);
	}

	@Override
	public void insertLotteryInfo(GiftRecord giftRecord) {
		giftRecordMapper.insertRecord(giftRecord);
	}

	@Override
	public boolean checkStatus(int userId, int type) {
		// 玩家等级大于等于15级
		if (playerMapper.selectByUserId(userId).getLevel() < LOTTERY_LEVE_LIMIT) {
			// 抱歉，等级不够，没有抽奖资格
			System.out.println("抱歉，您已经没有资格了！");
			return false;
		}
		try {
			int times = giftRecordMapper.selectLotteryTimes(userId, type);
			if (times > 1) {
				System.out.println("抱歉，您已经使用过资格了资格了！");
				return false;
			}
		} catch (Exception e) {
			// 出现异常，说明并没有进行抽奖，数据库中没数据
			System.out.println("恭喜您，还有一次抽奖资格");
			return true;
		}
		return true;
	}

}
