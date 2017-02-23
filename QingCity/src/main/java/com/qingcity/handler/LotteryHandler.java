package com.qingcity.handler;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.qingcity.constants.CmdConstant;
import com.qingcity.domain.GameResponse;
import com.qingcity.entity.Gift;
import com.qingcity.entity.GiftRecord;
import com.qingcity.entity.MsgEntity;
import com.qingcity.proto.Lottery.C2S_Lottery;
import com.qingcity.service.LotteryService;
import com.qingcity.util.LotteryUtil;
import com.qingcity.util.TimeUtil;

@Controller
public class LotteryHandler extends HandlerMsg implements CmdHandler {

	private static final Logger logger = LoggerFactory.getLogger(LotteryHandler.class);

	private static final Integer LOSE_LOTTERY_NUM = 50000;// 未中奖人数
	private static final Integer LOSE_LOTTERY_ID = -1;// 未中奖的id

	@Autowired
	private LotteryService lotteryService;

	@Autowired
	private GiftRecord giftRecord;

	@Override
	public void handleMsg(MsgEntity msgEntity, GameResponse response) throws Exception {
		if (isErrorMsg(msgEntity, response)) {
			logger.error("实际消息和原消息不符!长度不一致。");
			return;
		}
		switch (msgEntity.getCmdCode()) {// 根据命令码对应找到对应处理方法
		case CmdConstant.DRAW_A_LOTTERY:
			HandleDrawALottery(msgEntity, response);
			break;
		default:
			logger.warn("命令码[{}]找不到", msgEntity.getCmdCode());
		}
	}

	private synchronized void HandleDrawALottery(MsgEntity msgEntity, GameResponse response) {
		C2S_Lottery lottery = null;
		try {
			lottery = C2S_Lottery.parseFrom(msgEntity.getData());
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		int lotteryId = lottery.getType();
		int userId = lottery.getUserId();
		// 检查是否有资格
		if (!lotteryService.checkStatus(userId, lotteryId)) {
			System.out.println("很遗憾，您已经没有资格了");
			return;
		}
		// 检查当前票是否有剩余，当剩余为0的时候表明直接未中奖
		int count = lotteryService.checkTicketCount(lotteryId);
		if (count <= 0) {
			// 剩余票数为0,将当前玩家设置为未中奖
			SaveLotteryResult(lotteryId, userId, LOSE_LOTTERY_ID);
			// 返回未中奖的信息
			System.out.println("很遗憾，没票了，您没有中奖");
			return;
		}
		// 说明还有票。进行抽奖
		List<Gift> gifts = LotteryUtil.getGiftList(1);
		int none = 0;
		for (Gift gift : gifts) {
			none = LOSE_LOTTERY_NUM - gift.getCount();
		}
		gifts.add(new Gift(LOSE_LOTTERY_ID, "未中奖", none, "很遗憾您未中奖"));

		int result = LotteryUtil.lottery(LotteryUtil.CalulateOrignalRates(gifts));
		logger.debug("玩家" + userId + "于" + new Date() + "进行了类型为" + lotteryId + "的抽奖");
		SaveLotteryResult(lotteryId, userId, result);
		System.out.println("您获得的是" + result + "PS:-1表示未中奖");
		// 返回中奖信息

	}

	private void SaveLotteryResult(int lotteryId, int userId, int status) {
		giftRecord.setType(lotteryId);
		giftRecord.setStatus(status);
		giftRecord.setUserId(userId);
		giftRecord.setWinTime(TimeUtil.getCurrentTimestamp());
		lotteryService.insertLotteryInfo(giftRecord);
	}
}
