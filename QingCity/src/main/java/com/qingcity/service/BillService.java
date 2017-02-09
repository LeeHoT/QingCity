package com.qingcity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qingcity.entity.Bill;

@Service
public interface BillService {

	/**
	 * 检查订单状态
	 * 
	 * @param bid
	 *            订单id
	 * @param userId
	 *            玩家id
	 * @return 未完成付款返回false,否则返回true
	 */
	public boolean checkStatus(String bid, int userId);

	/**
	 * 保存订单信息
	 * 
	 * @param bill
	 *            订单对象
	 * @return 返回刚插入订单的对象，包含订单id
	 */
	public Bill saveBill(Bill bill);

	/**
	 * 根据玩家id查询玩家的所有订单信息
	 * 
	 * @param userId
	 * @return 订单信息
	 */
	public List<Bill> selectBillByUid(int userId);

	/**
	 * 根据订单id查询订单信息
	 * 
	 * @param bid
	 *            订单id
	 * @return 订单信息
	 */
	public Bill selectBillByBid(String bid);

	/**
	 * 根据订单id删除订单
	 * 
	 * @param bid
	 *            订单id
	 */
	public void deleteBillByBid(String bid);

	/**
	 * 根据玩家id删除订单信息(不推荐使用)
	 * 
	 * @param userId
	 */
	public void deleteBillByUid(int userId);
}
