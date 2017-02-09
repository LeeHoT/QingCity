package com.qingcity.dao;

import org.springframework.stereotype.Repository;

import com.qingcity.entity.Bill;

/**
 * 
 * @author leehotin
 * @Date 2017年2月9日 下午4:13:55
 * @Description 订单Mapper接口
 */
@Repository
public interface BillMapper {
	int deleteByPrimaryKey(Integer orderid);

	int insert(Bill record);

	int insertSelective(Bill record);

	Bill selectByPrimaryKey(Integer orderid);

	int updateByPrimaryKeySelective(Bill record);

	int updateByPrimaryKey(Bill record);
}