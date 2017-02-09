package com.qingcity.service.impl;

import java.util.List;

import com.qingcity.entity.Bill;
import com.qingcity.service.BillService;

/**
 * 
 * @author leehotin
 * @Date 2017年2月9日 下午7:04:56
 * @Description 订单service实现
 */
public class BillServiceImpl implements BillService {

	@Override
	public boolean checkStatus(String bid, int userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Bill saveBill(Bill bill) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bill> selectBillByUid(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bill selectBillByBid(String bid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBillByBid(String bid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBillByUid(int userId) {
		// TODO Auto-generated method stub

	}

}
