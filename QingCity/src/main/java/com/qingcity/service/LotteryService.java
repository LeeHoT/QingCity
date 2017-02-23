package com.qingcity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qingcity.entity.GiftRecord;

@Service
public interface LotteryService {

	/**
	 * 检查当前类型的票还剩余多少张
	 * 
	 * @param id
	 *            抢票类型id
	 * @return 剩余票数
	 */
	int checkTicketCount(int id);

	/**
	 * 检查玩家是否还有抽奖资格
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	boolean checkStatus(int userId, int type);

	/**
	 * 当确定某位玩家抽中时，更新余票数量
	 * 
	 * @param id
	 *            当前更新的票类型的id
	 * @param count
	 *            票减少的数量
	 */
	void updateTicketCount(int id, int count);

	/**
	 * 查询抢中某种票的玩家id列表
	 * 
	 * @param id
	 *            抢票的类型
	 * @return 抢中票的玩家列表
	 */
	List<GiftRecord> selectWinnerInfo(int type);

	/**
	 * 新增中奖信息信息
	 * 
	 * @param giftRecord
	 */
	void insertLotteryInfo(GiftRecord giftRecord);

}
